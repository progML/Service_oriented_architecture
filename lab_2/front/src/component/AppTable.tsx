import React from "react";
import {GET_Humans_Request, HumanTable, Order, SortBy} from "api/api";
import Human, {IHuman, IHumanKey} from "model/Human";
import {Button} from "react-bootstrap";


interface onTableUpdate {
	(request?: GET_Humans_Request): void
}

interface onTableDelete {
	(id: number): void
}

interface AppTableProps {
	table: HumanTable,  // latest table
	sort: SortBy, // AppTable is responsible for sorting in that creepy scheme
	loading: boolean,  // are we loading? shouldn't update then
	onTableUpdate: onTableUpdate, // Callback point
	onTableDelete: onTableDelete
}


export default class AppTable extends React.Component<AppTableProps, any> {

	outputWithSorting(key: string, sort: SortBy, onTableUpdate: onTableUpdate): JSX.Element {
		const keyIsCoordinates = key === "coordinates";
		const sortByCoordinates = sort.field === "x" || sort.field === "y";
		const sortBySameKey = key === sort.field;

		let changeSortFunction = undefined;
		let sortTextValue = sortBySameKey ?
			`${sort.order === Order.ASC ? "▲" : "▼"}` :
			"";
		if (keyIsCoordinates && sortByCoordinates)
			sortTextValue = `${sort.field} ${sort.order === Order.ASC ? "▲" : "▼"}`;

		changeSortFunction = () => {
			if (keyIsCoordinates && sortByCoordinates) { // Сортировка по координатам, переключаем координату и порядок
				// Порядок сортировки: Х АСК, Х ДЕСК, У АСК, У ДЕСК
				let order: SortBy =
					sort.field === "x" ?
						sort.order === Order.ASC ?
							{field: "x", order: Order.DESC} :
							{field: "y", order: Order.ASC} :
						sort.order === Order.ASC ?
							{field: "y", order: Order.DESC} :
							{field: "x", order: Order.ASC}
				onTableUpdate({
					sort: order
				});

			} else if (sortBySameKey) { // Сортировка по тому же ключу - просто меняем порядок
				onTableUpdate({
					sort: {field: key, order: sort.order === Order.ASC ? Order.DESC : Order.ASC}
				});
			} else if (keyIsCoordinates) {
				onTableUpdate({
					sort: {field: "x", order: Order.ASC}
				});
			} else {
				onTableUpdate({
					sort: {field: key, order: Order.ASC}
				})
			}
		}

		return <th scope="col" className={"sortable"} onClick={changeSortFunction}>{key} {sortTextValue}</th>
	}

	/**
	 * Iterate over keys and make some TH of them
	 */
	prepareKeys(keys: IHumanKey[], sort: SortBy, onTableUpdate: onTableUpdate): JSX.Element {
		return <tr>{keys.map(
			(key: string) => this.outputWithSorting(key, sort, onTableUpdate)
		)}
			<th>
				Del by id
			</th>
		</tr>
	}

	/**
	 * Iterate through some humans and make Table Row of them
	 */
	prepareValues(values: IHuman[], onTableDelete: onTableDelete): JSX.Element[] {
		return values.map((human) =>
			<tr key={human.id}>{
				Object.values(Human.getDict(human)).map(
					(value) => <td>{value}</td>
				)}
				<td>
					<Button variant={"outline-danger"} onClick={() => onTableDelete(human.id)}>
						DEL
					</Button>
				</td>
			</tr>
		)
	}

	shouldComponentUpdate(nextProps: Readonly<AppTableProps>,
	                      nextState: Readonly<any>,
	                      nextContext: any): boolean {
		return !nextProps.loading;
	}

	render() {
		const {table, sort, onTableUpdate, onTableDelete} = this.props;
		if (table.length > 0) {
			const keys = Object.keys(table[0]) as IHumanKey[]; // simple trick, but quite unbreakable
			return <table className="table table-bordered">
				<thead>{this.prepareKeys(keys, sort, onTableUpdate)}</thead>
				<tbody>{this.prepareValues(table, onTableDelete)}</tbody>
			</table>
		} else {
			return <div>
				Nothing to show, sorry
			</div>
		}
	}
}

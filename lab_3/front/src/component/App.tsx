import React from 'react';
import AppTable from "./AppTable";
import API, {GET_Humans_Request, GET_Humans_Response, HumanTable, Order} from "api/api";
import {Accordion, Button, Container, Modal} from "react-bootstrap";
import {IHuman} from "model/Human";
import Human from "component/form/Human"
import {AlertBox, AlertMessage} from "./Alerts";
import Search from "component/form/Search";
import AppPagination from "component/AppPagination"
import {AxiosResponse} from "axios";

interface AppProps {

}

interface AppState {
	loading: boolean,
	table: HumanTable,
	totalItems: number,
	pageIndex: number,
	pageSize: number,
	alerts: AlertMessage[]
	request?: GET_Humans_Request // Request can be null - nothing sent
}

function PaddedContainer(props: React.HTMLAttributes<HTMLElement>): JSX.Element {
	return <Container fluid={"md"} className={"mt-3"} {...props}>
		{props.children}
	</Container>
}

interface NewEntryDialogProps {
	onSubmit?: {
		(human: IHuman): void;
	}
}

interface NewEntryDialogState {
	show: boolean,
	human?: IHuman
}

class NewEntryDialog extends React.Component<NewEntryDialogProps, NewEntryDialogState> {
	constructor(props: NewEntryDialogProps) {
		super(props);

		this.handleShow = this.handleShow.bind(this);
		this.handleClose = this.handleClose.bind(this);
		this.handleSave = this.handleSave.bind(this);
		this.handleChange = this.handleChange.bind(this);

		this.state = {
			show: false,
			human: undefined
		}
	}

	shouldComponentUpdate(nextProps: Readonly<NewEntryDialogProps>,
	                      nextState: Readonly<NewEntryDialogState>,
	                      nextContext: any): boolean {
		return nextState.show !== this.state.show;
	}

	handleShow() {
		this.setState({show: true});
	}

	handleClose() {
		this.setState({show: false})
	}

	handleSave() {
		if (this.state.human === undefined)
			return;
		this.props.onSubmit?.(this.state.human);
	}

	handleChange(human: IHuman) {
		this.setState({human: human});
	}

	render() {
		const {show} = this.state;

		return <>
			<Button variant="success" className={"loadable"} onClick={this.handleShow}>
				Add
			</Button>

			<Modal show={show} onHide={this.handleClose}
			       size="lg">
				<Modal.Header closeButton>
					<Modal.Title>Add new entry</Modal.Title>
				</Modal.Header>
				<Modal.Body>
					<Human.Form onChange={this.handleChange}/>
				</Modal.Body>
				<Modal.Footer>
					<Button variant="secondary" onClick={this.handleClose}>
						Close
					</Button>
					<Button variant="primary" onClick={this.handleSave}>
						Save
					</Button>
				</Modal.Footer>
			</Modal>
		</>
	}
}

const defaultRequest: GET_Humans_Request = {
	sort: {
		field: "id",
		order: Order.ASC
	},
	size: 5,
	page: 0
};

export default class App extends React.Component<AppProps, AppState> {
	constructor(props: AppProps) {
		super(props);

		this.updateTable = this.updateTable.bind(this);
		this.onTableUpdateHappened = this.onTableUpdateHappened.bind(this);
		this.updateTableById = this.updateTableById.bind(this);
		this.updateTableByName = this.updateTableByName.bind(this);
		this.deleteFromTableByMood = this.deleteFromTableByMood.bind(this);
		this.deleteFromTableById = this.deleteFromTableById.bind(this);

		this.createTeam = this.createTeam.bind(this);
		this.addUserToTheTeam = this.addUserToTheTeam.bind(this);

		this.addAlert = this.addAlert.bind(this);
		this.postHuman = this.postHuman.bind(this);
		this.hideAlert = this.hideAlert.bind(this);

		this.state = {
			loading: false,
			table: [],
			alerts: [],
			totalItems: 0,
			pageIndex: 0,
			pageSize: 0
		};
	}

	componentDidMount() {
		this.updateTable(defaultRequest);
	}

	addAlert(message: string, type: "danger" | "success") {
		this.setState({
			alerts: this.state.alerts.concat([
				{type: type, message: message, show: true}
			])
		})
	}

	hideAlert(id: number) {
		const alerts = this.state.alerts;
		if (id >= 0 && id < alerts.length) {
			alerts[id].show = false;
			this.setState({alerts: alerts});
		}
	}

	/**
	 * Update if loading is changed
	 */
	shouldComponentUpdate(nextProps: Readonly<AppProps>,
	                      nextState: Readonly<AppState>,
	                      nextContext: any): boolean {
		return !this.state.loading || nextState.loading !== this.state.loading
	}

	updateTableById(id: number) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.getHumanById(id)
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.setState({
					totalItems: 1,
					pageIndex: 0,
					pageSize: 5,
					table: [response],
				});
			})
			.catch((error) => { // got error, clean table and continue
				/* todo: oh no, error happened */
				this.setState({
					table: [],
					totalItems: 0,
					pageIndex: 0,
				})
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false, request: defaultRequest})
			});
	}


	updateTableByName(name: string) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});
		API.getHumanByName(name)
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.setState({
					totalItems: response.length,
					pageIndex: 0,
					pageSize: 5,
					table: response,
				});
			})
			.catch((error) => { // got error, clean table and continue
				/* todo: oh no, error happened */
				this.setState({
					table: [],
					totalItems: 0,
					pageIndex: 0,
				})
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false, request: defaultRequest})
			});
	}

	deleteFromTableByMood(mood: string) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.deleteHumanByMood(mood)
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.addAlert(response, "success");
			})
			.catch((error) => { // got error, clean table and continue
				this.addAlert("Error happened", "danger");
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false});
				this.updateTable();
			});
	}

	addUserToTheTeam(userId: number, teamName: string) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.addUserToTheTeam(userId, teamName)
			.then((axios_response) => { // got success, fill table and continue
				this.addAlert("User added", "success");
			})
			.catch((error) => { // got error, clean table and continue
				this.addAlert("Error happened", "danger");
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false});
				this.updateTable();
			});
	}

	createTeam(teamName: string) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.createTeam(teamName)
			.then((axios_response) => { // got success, fill table and continue
				this.addAlert("Team created", "success");
			})
			.catch((error) => { // got error, clean table and continue
				this.addAlert("Error happened", "danger");
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false});
			});
	}

	deleteFromTableById(id: number) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.deleteFromTableById(id)
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.addAlert(response.message, "success");
			})
			.catch((error) => { // got error, clean table and continue
				this.addAlert("Error happened", "danger");
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false});
				this.updateTable();
			});
	}

	/**
	 * Make request and update table
	 */
	updateTable(request?: GET_Humans_Request) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true})

		request = Object.assign(this.state.request || {}, request || {}); // Если передали null/undef, заменяем из запроса
		this.onTableUpdateHappened(API.getHumans(request), request);
	}

	onTableUpdateHappened(promise: Promise<AxiosResponse<GET_Humans_Response>>, request: GET_Humans_Request) {
		promise
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.setState({
					totalItems: response.totalItems,
					pageIndex: response.pageIndex,
					pageSize: response.pageSize,
					table: response.list,
				});
			})
			.catch((error) => { // got error, clean table and continue
				/* todo: oh no, error happened */
				this.setState({
					table: [],
					totalItems: 0,
					pageIndex: 0,
				})
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false, request: request})
			});
	}

	/**
	 * Post human from form
	 */
	postHuman(human: IHuman) {
		// if loading - ignore
		if (this.state.loading)
			return;

		// cool, set loading and wait
		this.setState({loading: true});

		API.postHuman({human: human})
			.then((axios_response) => { // got success, fill table and continue
				const response = axios_response.data;
				this.addAlert(response.message, "success");
			})
			.catch((error) => { // got error, clean table and continue
				this.addAlert("Error happened", "danger");
			})
			.finally(() => { // after everything - stop loading and let user do his business
				this.setState({loading: false});
				this.updateTable();
			});
	}

	render() {
		const {
			table, loading,
			alerts, request,
			totalItems, pageSize, pageIndex
		} = this.state;

		const onSubmitFindById = (e: React.FormEvent) => {
			e.preventDefault();
			const findByForm = e.target as HTMLFormElement;
			if (findByForm["app-field-human-id"].value)
				this.updateTableById(parseInt(findByForm["app-field-human-id"].value));
		}

		const onSubmitFindByName = (e: React.FormEvent) => {
			e.preventDefault();
			const findByForm = e.target as HTMLFormElement;
			if (findByForm["app-field-human-name"].value)
				this.updateTableByName(findByForm["app-field-human-name"].value);
		}

		const onSubmitDeleteByMood = (e: React.FormEvent) => {
			e.preventDefault();
			const findByForm = e.target as HTMLFormElement;
			if (findByForm["app-field-human-mood"].value)
				this.deleteFromTableByMood(findByForm["app-field-human-mood"].value);
		}

		const onSubmitCreateTeam = (e: React.FormEvent) => {
			e.preventDefault();
			const findByForm = e.target as HTMLFormElement;
			if (findByForm["app-field-team-name"].value)
				this.createTeam(findByForm["app-field-team-name"].value);
		}

		const onSubmitAddUserToTeam = (e: React.FormEvent) => {
			e.preventDefault();
			const findByForm = e.target as HTMLFormElement;
			if (findByForm["app-field-add-to-team-name"].value && findByForm["app-field-add-to-team-user-id"].value)
				this.addUserToTheTeam(
					parseInt(findByForm["app-field-add-to-team-user-id"].value),
					findByForm["app-field-add-to-team-name"].value
				);
		}

		return <div className={`application ${loading ? "still-loading" : ""}`}>
			<div className={"loading-marker"}/>

			<PaddedContainer>
				<Button className={"loadable"}
				        onClick={() => this.updateTable()}>
					Update
				</Button>
			</PaddedContainer>

			<PaddedContainer>
				<Accordion>
					<Accordion.Item eventKey="0">
						<Accordion.Header>Search form</Accordion.Header>
						<Accordion.Body>
							<Search.Form onTableUpdate={this.updateTable}/>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="1">
						<Accordion.Header>Find by ID</Accordion.Header>
						<Accordion.Body>
							<form onSubmit={onSubmitFindById}>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-human-id"}
									       className={"col-auto col-form-label"}>ID</label>
									<div className={"col"}>
										<input id={"app-field-human-id"} name={"app-field-human-id"}
										       className={"form-control"} type={"number"}/>
									</div>
								</div>
								<Button type={"submit"}>
									Find by ID
								</Button>
							</form>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="2">
						<Accordion.Header>Find by Name</Accordion.Header>
						<Accordion.Body>
							<form onSubmit={onSubmitFindByName}>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-human-name"}
									       className={"col-auto col-form-label"}>Name</label>
									<div className={"col"}>
										<input id={"app-field-human-name"} name={"app-field-human-name"}
										       className={"form-control"} type={"text"}/>
									</div>
								</div>
								<Button type={"submit"}>
									Find by Name
								</Button>
							</form>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="3">
						<Accordion.Header>Delete by Mood</Accordion.Header>
						<Accordion.Body>
							<form onSubmit={onSubmitDeleteByMood}>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-human-mood"}
									       className={"col-auto col-form-label"}>Mood</label>
									<div className={"col"}>
										<select id={"app-field-human-mood"} name={"app-field-human-mood"}
										        className={"form-select"}>
											<option value={""}>...</option>
											<option value={"LONGING"}>LONGING</option>
											<option value={"GLOOM"}>GLOOM</option>
											<option value={"APATHY"}>APATHY</option>
											<option value={"FRENZY"}>FRENZY</option>
										</select>
									</div>
								</div>
								<Button type={"submit"}>
									Delete by Mood
								</Button>
							</form>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="4">
						<Accordion.Header>Create Team</Accordion.Header>
						<Accordion.Body>
							<form onSubmit={onSubmitCreateTeam}>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-team-name"}
									       className={"col-auto col-form-label"}>Team Name</label>
									<div className={"col"}>
										<input id={"app-field-team-name"} name={"app-field-team-name"}
										       className={"form-control"} type={"text"}/>
									</div>
								</div>
								<Button type={"submit"}>
									Create Team
								</Button>
							</form>
						</Accordion.Body>
					</Accordion.Item>
					<Accordion.Item eventKey="5">
						<Accordion.Header>Add to team</Accordion.Header>
						<Accordion.Body>
							<form onSubmit={onSubmitAddUserToTeam}>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-add-to-team-user-id"}
									       className={"col-auto col-form-label"}>User ID</label>
									<div className={"col"}>
										<input id={"app-field-add-to-team-user-id"}
										       name={"app-field-add-to-team-user-id"}
										       className={"form-control"} type={"number"}/>
									</div>
								</div>
								<div className={"row mb-3"}>
									<label htmlFor={"app-field-add-to-team-name"}
									       className={"col-auto col-form-label"}>Team Name</label>
									<div className={"col"}>
										<input id={"app-field-add-to-team-name"} name={"app-field-add-to-team-name"}
										       className={"form-control"} type={"text"}/>
									</div>
								</div>
								<Button type={"submit"}>
									Add to team
								</Button>
							</form>
						</Accordion.Body>
					</Accordion.Item>
				</Accordion>
			</PaddedContainer>

			<PaddedContainer>
				<NewEntryDialog onSubmit={this.postHuman}/>
			</PaddedContainer>

			<PaddedContainer>
				<div className={"loading-wrapper"}>
					<AppPagination onTableUpdate={this.updateTable}
					               totalItems={totalItems}
					               pageIndex={pageIndex}
					               pageSize={pageSize}/>
				</div>
			</PaddedContainer>

			<PaddedContainer>
				<AlertBox alerts={alerts} onHideAlert={this.hideAlert}/>
				<div className={"loading-wrapper my-3"}>
					<AppTable table={table} sort={request?.sort || {
						field: "id",
						order: Order.ASC
					}} loading={loading} onTableUpdate={this.updateTable} onTableDelete={this.deleteFromTableById}/>
				</div>
			</PaddedContainer>

			{/*<div className={"console-container"}> {JSON.stringify(request)} </div>*/}
		</div>
	}
}

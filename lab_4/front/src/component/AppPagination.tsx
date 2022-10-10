import React, {ChangeEvent} from 'react';
import {Pagination} from "react-bootstrap";
import {GET_Humans_Request} from "api/api";


interface onTableUpdate {
	(request?: GET_Humans_Request): void
}

interface AppPaginationProps {
	totalItems: number,
	pageSize: number,
	pageIndex: number,
	onTableUpdate: onTableUpdate, // Callback point
}

const availablePageSizes = [5, 10, 15];

function AppPagination(props: AppPaginationProps) {
	console.log(props);
	const {pageSize, pageIndex, totalItems, onTableUpdate} = props;
	const maxPageNumber = Math.ceil(totalItems / pageSize);

	const onChangePageSize = (e: ChangeEvent) => {
		e.preventDefault();
		const selectPageSize = e.target as HTMLSelectElement;
		if (selectPageSize && selectPageSize?.value)
			onTableUpdate({page: 0, size: parseInt(selectPageSize.value)});
	}

	const onChangePage = (e: ChangeEvent) => {
		e.preventDefault();
		const selectPage = e.target as HTMLSelectElement;
		if (selectPage && selectPage?.value)
			onTableUpdate({page: parseInt(selectPage.value)});
	}

	const NextPage = () => {
		onTableUpdate({page: pageIndex + 1});
	}

	const PrevPage = () => {
		onTableUpdate({page: pageIndex - 1});
	}

	return <div>
		<div className="mb-3 row">
			<label htmlFor="app-pagination-field-page-size" className="col-auto col-form-label">Page size</label>
			<div className="col-auto">
				<select id={"app-pagination-field-page-size"}
				        name={"app-pagination-field-page-size"}
				        className={"form-select d-inline-block"}
				        value={pageSize}
				        onChange={onChangePageSize}>
					{
						availablePageSizes.map(size =>
							<option value={size}>{size}</option>
						)
					}
				</select>
			</div>
		</div>
		<Pagination>
			<Pagination.Prev disabled={pageIndex === 0} onClick={PrevPage}/>
			<div className={"mx-3 d-flex flex-row justify-content-start align-items-center"}>
				<div className="col-auto">
					<select id={"app-pagination-field-page-select"}
					        name={"app-pagination-field-page-select"}
					        className={"form-select d-inline-block"}
					        value={pageIndex}
					        onChange={onChangePage}>
						{
							Array.from(function* () {
								for (let i = 0; i < maxPageNumber; i++) {
									yield <option value={i}>{i + 1}</option>;
								}
							}())
						}
					</select>
				</div>
				<label htmlFor={"app-pagination-field-page-select"}
				       className={"d-inline-block ps-2"}>{`/ ${maxPageNumber}`}</label>
			</div>
			<Pagination.Next disabled={pageIndex === maxPageNumber - 1} onClick={NextPage}/>
		</Pagination>
	</div>
}

export default AppPagination;

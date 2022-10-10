import {IHuman} from "model/Human";
import axios, {AxiosResponse} from "axios";
import * as qs from 'qs';

// BASE MAIN_SERVICE API URL

const mainBaseUrl = "http://localhost:8080/api"

const getHumansUrl = mainBaseUrl + "/humanBeing";
const getHumanByIdUrl = (id: number): string => mainBaseUrl + `/humanBeing/${id}`;
const getHumanByNameUrl = (name: string): string => mainBaseUrl + `/humanBeing/findName/${name}`;
const deleteFromTableByIdUrl = (id: number): string => mainBaseUrl + `/humanBeing/${id}`;
const deleteHumanByMoodUrl = (mood: string): string => mainBaseUrl + `/humanBeing/deleteMood/${mood}`;
const postHumansUrl = mainBaseUrl + "/humanBeing";

const secondBaseUrl = "http://localhost:8084/api";
const createTeamUrl = (teamName: string): string => secondBaseUrl + `/team/create/${teamName}`;
const addUserToTeamUrl = (userId: number): string => secondBaseUrl + `/team/add/${userId}`;

/* -- Some shit to do -- */

type HumanTable = IHuman[];

enum Order {
	ASC = "asc",
	DESC = "desc"
}

interface SortBy {
	field: string;
	order: Order;
}

interface GET_Humans_Request {
	sort?: SortBy;
	size?: number;
	page?: number;
	searchData?: {
		[key: string]: string | number | string[] | number[]
	}
}

interface GET_Humans_Response {
	pageSize: number;
	pageIndex: number;
	totalItems: number;
	list: HumanTable;
}

/* -- todo: change API to real one */

const defaultRequest: GET_Humans_Request = {
	sort: {
		field: "id",
		order: Order.ASC
	},
	size: 5,
	page: 0,
}

async function getHumans(request?: GET_Humans_Request): Promise<AxiosResponse<GET_Humans_Response>> {
	const realRequest = Object.assign(defaultRequest, request || {});
	const params = {
		sort: realRequest.sort?.field + "_" + realRequest.sort?.order,
		size: realRequest.size,
		page: realRequest.page,
		...realRequest.searchData
	};

	return axios.get<GET_Humans_Response>(getHumansUrl, {
		params: params,
		paramsSerializer: params => {
			return qs.stringify(params, {indices: false})
		}
	});
}

interface POST_Human_Request {
	human: IHuman
}

interface POST_Human_Response {
	humanBeingId: number;
	message: string
}

async function postHuman(request: POST_Human_Request): Promise<AxiosResponse<POST_Human_Response>> {
	const params: any = {
		...request.human
	};

	delete params.team.id;

	return axios.post<POST_Human_Response>(postHumansUrl, params);
}

async function getHumanById(id: number): Promise<AxiosResponse<IHuman>> {
	return axios.get<IHuman>(getHumanByIdUrl(id));
}

async function getHumanByName(name: string): Promise<AxiosResponse<IHuman[]>> {
	return axios.get<IHuman[]>(getHumanByNameUrl(name));
}

async function deleteHumanByMood(mood: string): Promise<AxiosResponse> {
	return axios.delete(deleteHumanByMoodUrl(mood));
}

async function deleteFromTableById(id: number): Promise<AxiosResponse> {
	return axios.delete(deleteFromTableByIdUrl(id));
}

/* -- */

async function createTeam(teamName: string): Promise<AxiosResponse> {
	return axios.get(createTeamUrl(teamName));
}

async function addUserToTheTeam(userId: number, teamName: string): Promise<AxiosResponse> {
	return axios.post(addUserToTeamUrl(userId), {"teamName": teamName});
}

export type {HumanTable, GET_Humans_Request, GET_Humans_Response, SortBy}
export {Order}
export default {
	getHumans,
	postHuman,
	getHumanById,
	getHumanByName,
	deleteHumanByMood,
	deleteFromTableById,
	/* -- */
	createTeam,
	addUserToTheTeam
}

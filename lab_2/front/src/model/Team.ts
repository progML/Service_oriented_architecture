interface ITeam {
	id: number;
	teamName: string;
}

function getDict(team: ITeam): any {
	return {
		id: team.id,
		teamName: team.teamName,
	}
}

function getString(team: ITeam): any {
	return `Team(${team.id}): ${team.teamName}`;
}

export type {ITeam}
export default {getDict, getString}

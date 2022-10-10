interface ICoordinates {
	id: number;
	x: number;
	y: number;
}

function getDict(coordinates: ICoordinates): object {
	return {
		id: coordinates.id,
		x: coordinates.x,
		y: coordinates.y,
	}
}

function getString(coordinates: ICoordinates): string {
	return `Coordinates(x=${coordinates.x}, y=${coordinates.y})`;
}

export type {ICoordinates}
export default {getDict, getString}

interface ICar {
	id: number;
	cool?: boolean;
}


function getDict(car: ICar): object {
	return {
		id: car.id,
		cool: car.cool,
	}
}

function getString(car: ICar): string {
	return `Car(${car.id}): ${car.cool ? "COOL" : "NOT COOL"}`;
}

export type {ICar}
export default {getDict, getString}

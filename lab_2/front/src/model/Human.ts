import Coordinates, {ICoordinates} from "./Coordinates";
import Team, {ITeam} from "./Team";
import Car, {ICar} from "./Car";

interface IHuman {
	id: number;
	name: string;
	coordinates: ICoordinates;
	creationDate: string;
	realHero?: boolean;
	hasToothpick?: boolean;
	impactSpeed: number;
	minutesOfWaiting: number;
	weaponType: string;
	mood: string;
	car: ICar;
	team: ITeam;
}

type IHumanKey = Extract<keyof IHuman, string>;

/**
 * Сериализует все внутренние объекты и выдает словарь с адекватными ключами
 */
function getDict(human: IHuman): object {
	return {
		id: human.id,
		name: human.name,
		coordinates: Coordinates.getString(human.coordinates),
		creationDate: new Date(human.creationDate).toDateString(),
		realHero: human.realHero ? "YES" : "NO",
		hasToothpick: human.hasToothpick ? "YES" : "NO",
		impactSpeed: human.impactSpeed,
		minutesOfWaiting: human.minutesOfWaiting,
		weaponType: human.weaponType,
		mood: human.mood,
		car: Car.getString(human.car),
		team: Team.getString(human.team),
	}
}

export type {IHuman, IHumanKey}
export default {getDict}

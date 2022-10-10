import {IHuman, IHumanKey} from "model/Human"
import React, {ChangeEvent, ChangeEventHandler} from "react";
import Coordinates from "component/form/Coordinates"
import Car from "component/form/Car"
import Team from "component/form/Team"
import Datetime from 'react-datetime';
import {Moment} from "moment";

type InputEvents = {
	[key in keyof IHuman]?: ChangeEventHandler;
};

type InputTypes = {
	[key in keyof IHuman]?: string | any;
};

type InputType = {
	key: IHumanKey,
	value: string | any;
};

interface FormProps {
	human?: IHuman,
	baseName?: string,
	onChange?: { (human: IHuman): void; }
}

type KeyValueFormData = {
	[key: string]: string | number | object | boolean | Moment;
}

type HumanFormFields = {
	[key in HumanFormFieldName]: {
		(className?: string, labelClassName?: string): JSX.Element
	}
}

type HumanFormFieldName =
	"human-field-name" |
	"human-field-coordinates" |
	"human-field-creation-date" |
	"human-field-real-hero" |
	"human-field-has-toothpick" |
	"human-field-impact-speed" |
	"human-field-minutes-of-waiting" |
	"human-field-weapon-type" |
	"human-field-mood" |
	"human-field-car" |
	"human-field-team";

class Form extends React.Component<FormProps, any> {
	formData: KeyValueFormData;

	constructor(props: FormProps) {
		super(props);

		this.fieldChanged = this.fieldChanged.bind(this);
		this.getHuman = this.getHuman.bind(this);
		this.generateFields = this.generateFields.bind(this);

		this.formData = {};
	}

	fieldChanged(key: string, value: string | object) {
		this.formData[key] = value;
		this.props.onChange?.(this.getHuman()); // Callback if not null
	}

	public getHuman() {
		const baseName = this.props.baseName || "form-";
		const human: IHuman = {
			id: 0,
			name: this.formData[baseName + "human-field-name"] as string,
			coordinates: {
				id: 0,
				x: parseFloat(this.formData[baseName + "coordinates-field-x"] as string),
				y: parseFloat(this.formData[baseName + "coordinates-field-y"] as string),
			},
			creationDate: this.formData[baseName + "human-field-creation-date"] as string,
			realHero:
				this.formData[baseName + "human-field-real-hero"] === "true" ? true :
					this.formData[baseName + "human-field-real-hero"] === "false" ? false :
						undefined,
			hasToothpick:
				this.formData[baseName + "human-field-has-toothpick"] === "true" ? true :
					this.formData[baseName + "human-field-has-toothpick"] === "false" ? false :
						undefined,
			impactSpeed: parseInt(this.formData[baseName + "human-field-impact-speed"] as string),
			minutesOfWaiting: parseInt(this.formData[baseName + "human-field-minutes-of-waiting"] as string),
			weaponType: this.formData[baseName + "human-field-weapon-type"] as string,
			mood: this.formData[baseName + "human-field-mood"] as string,
			car: {
				id: 0,
				cool:
					this.formData[baseName + "car-field-cool"] === "true" ? true :
						this.formData[baseName + "car-field-cool"] === "false" ? false :
							undefined
			},
			team: {
				id: 0,
				teamName: this.formData[baseName + "team-field-name"] as string
			},
		}
		return human;
	}

	public generateFields(): HumanFormFields {
		const {human} = this.props;
		const baseName = this.props.baseName || "form-";
		const fieldChanged = this.fieldChanged;
		const onChange = (event: ChangeEvent) => {
			const field = event.target as HTMLInputElement;
			const key = field.getAttribute('name');

			if (key === null)
				return;

			const value = field.value;
			onChangeExplicit(key, value);
		};

		const onChangeExplicit = (key: string, value: string) => {
			fieldChanged(key, value);
		};

		const DateTimeOnChange = (t: Moment | string) => {
			if (typeof t === "string") {
				onChangeExplicit(baseName + "human-field-creation-date", "");
			} else {
				const m = t as Moment;
				onChangeExplicit(baseName + "human-field-creation-date", m?.toISOString());
			}
		}

		// old init value - initialValue={human ? new Date(human.creationDate) : new Date(Date.now())}

		return {
			// Поле с именем
			"human-field-name": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-name"} className={labelClassName}>Name</label>
					<input id={baseName + "human-field-name"} name={baseName + "human-field-name"}
					       className={"form-control"}
					       value={human?.name} onChange={onChange}/>
				</div>,

			// Поле с координатами
			"human-field-coordinates": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<div className={labelClassName}>Coordinates</div>
					<div>
						{Coordinates.getForm(baseName, human?.coordinates, onChange)}
					</div>
				</div>,

			// Дата создания
			"human-field-creation-date": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-creation-date"} className={labelClassName}>Creation
						date</label>
					<Datetime inputProps={
						{name: baseName + "human-field-creation-date", id: baseName + "human-field-creation-date"}
					}
					          onChange={DateTimeOnChange}/>
				</div>,

			// Настоящий герой
			"human-field-real-hero": (className = "mb-3", labelClassName = "form-check-label") => {
				const realHeroValue = (human?.realHero) ? human.realHero.toString() : "undefined";
				return <div className={className}>
					<span className={"d-inline-block col-form-label"}>
						<label htmlFor={baseName + "human-field-real-hero"}
						       className={labelClassName}>Has toothpick</label>
						<select id={baseName + "human-field-real-hero"}
						        name={baseName + "human-field-real-hero"}
						        className={"form-select"}
						        onChange={onChange}>
							<option value={"undefined"} selected={realHeroValue === "undefined"}>...</option>
							<option value={"false"} selected={realHeroValue === "false"}>False</option>
							<option value={"true"} selected={realHeroValue === "true"}>True</option>
						</select>
					</span>
				</div>
			},

			// Зубочистка
			"human-field-has-toothpick": (className = "mb-3", labelClassName = "form-check-label") => {
				const toothpickValue = (human?.hasToothpick) ? human.hasToothpick.toString() : "undefined";
				return <div className={className}>
					<span className={"d-inline-block col-form-label"}>
						<label htmlFor={baseName + "human-field-has-toothpick"}
						       className={labelClassName}>Has toothpick</label>
						<select id={baseName + "human-field-has-toothpick"}
						        name={baseName + "human-field-has-toothpick"}
						        className={"form-select"}
						        onChange={onChange}>
							<option value={"undefined"} selected={toothpickValue === "undefined"}>...</option>
							<option value={"false"} selected={toothpickValue === "false"}>False</option>
							<option value={"true"} selected={toothpickValue === "true"}>True</option>
						</select>
					</span>
				</div>
			},

			// Влияние скорости
			"human-field-impact-speed": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-impact-speed"} className={labelClassName}>Impact
						speed</label>
					<input id={baseName + "human-field-impact-speed"} name={baseName + "human-field-impact-speed"}
					       className={"form-control"}
					       type={"number"} value={human?.impactSpeed} onChange={onChange}/>
				</div>,

			// Время ожидания
			"human-field-minutes-of-waiting": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-minutes-of-waiting"} className={labelClassName}>Minutes of
						waiting</label>
					<input id={baseName + "human-field-minutes-of-waiting"}
					       name={baseName + "human-field-minutes-of-waiting"}
					       className={"form-control"}
					       type={"number"} value={human?.minutesOfWaiting} onChange={onChange}/>
				</div>,

			// Тип оружия
			"human-field-weapon-type": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-weapon-type"} className={labelClassName}>Weapon type</label>
					<select id={baseName + "human-field-weapon-type"} name={baseName + "human-field-weapon-type"}
					        className={"form-select"} value={human?.weaponType} onChange={onChange}>
						<option value={""}>...</option>
						<option value={"PISTOL"}>PISTOL</option>
						<option value={"SHOTGUN"}>SHOTGUN</option>
						<option value={"MACHINE_GUN"}>MACHINE_GUN</option>
						<option value={"NULL"}>NULL</option>
					</select>
				</div>,

			// Настроение
			"human-field-mood": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<label htmlFor={baseName + "human-field-mood"} className={labelClassName}>Mood</label>
					<select id={baseName + "human-field-mood"} name={baseName + "human-field-mood"}
					        className={"form-select"} value={human?.mood} onChange={onChange}>
						<option value={""}>...</option>
						<option value={"LONGING"}>LONGING</option>
						<option value={"GLOOM"}>GLOOM</option>
						<option value={"APATHY"}>APATHY</option>
						<option value={"FRENZY"}>FRENZY</option>
					</select>
				</div>,

			// Car
			"human-field-car": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<div className={labelClassName}>Car</div>
					<div>
						{Car.getForm(baseName, human?.car, onChange)}
					</div>
				</div>,

			// Team
			"human-field-team": (className = "mb-3", labelClassName = "form-label") =>
				<div className={className}>
					<div className={labelClassName}>Team</div>
					<div>
						{Team.getForm(baseName, human?.team, onChange)}
					</div>
				</div>
		}
	}

	render() {
		const fields = this.generateFields();

		return <form style={{maxWidth: 800}} onSubmit={(e) => e.preventDefault()}>
			{fields["human-field-name"]()}
			{fields["human-field-coordinates"]()}
			{fields["human-field-creation-date"]()}

			<div className="row mb-3">
				{fields["human-field-real-hero"]("col-md-auto")}
				{fields["human-field-has-toothpick"]("col-md-auto")}
			</div>

			<div className="row mb-3">
				{fields["human-field-impact-speed"]("col-md-6")}
				{fields["human-field-minutes-of-waiting"]("col-md-6")}
			</div>

			<div className="row mb-3">
				{fields["human-field-weapon-type"]("col-md-6")}
				{fields["human-field-mood"]("col-md-6")}
			</div>

			<div className="row mb-3">
				{fields["human-field-car"]("col-md-6")}
				{fields["human-field-team"]("col-md-6")}
			</div>
		</form>
	}
}

export type {HumanFormFieldName, HumanFormFields, Form as HumanForm}
export default {
	Form
}

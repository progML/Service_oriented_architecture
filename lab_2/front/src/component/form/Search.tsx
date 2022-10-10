import React from "react";
import Human, {HumanForm} from "component/form/Human";
import {Button} from "react-bootstrap";
import {GET_Humans_Request} from "api/api";

function PaddedRow(props: React.HTMLAttributes<HTMLElement>): JSX.Element {
	return <div className={"row mb-3"} {...props}>
		{props.children}
	</div>
}

function InputRowSeparator(props: React.HTMLAttributes<HTMLElement>): JSX.Element {
	return <div className={"col-md-auto d-flex align-items-end"}>
		<span className={"col-form-label"}>-</span>
	</div>
}

interface onTableUpdate {
	(request?: GET_Humans_Request): void
}

interface SearchProps {
	onTableUpdate: onTableUpdate, // Callback point
}

class Form extends React.Component<SearchProps, any> {
	formFrom: HumanForm;
	formTo: HumanForm;

	constructor(props: SearchProps) {
		super(props);

		this.processForm = this.processForm.bind(this);

		this.formFrom = new Human.Form({baseName: "from"});
		this.formTo = new Human.Form({baseName: "to"});
	}

	processForm() {
		const {onTableUpdate} = this.props;
		const searchObject: {
			[key: string]: string | number | string[] | number[]
		} = {};

		const is_valid = (value: any) => value !== undefined && value !== null && !isNaN(value);

		const fromHuman = this.formFrom.getHuman();
		const toHuman = this.formTo.getHuman();

		if (fromHuman.name) searchObject["name"] = fromHuman.name;
		if (fromHuman.weaponType) searchObject["weaponType"] = fromHuman.weaponType;
		if (fromHuman.mood) searchObject["mood"] = fromHuman.mood;

		// do something about it - double undefined check
		if (fromHuman.car?.cool !== undefined && is_valid(fromHuman.car?.cool))
			searchObject["cool"] = fromHuman.car.cool.toString();
		if (fromHuman.team?.teamName) searchObject["teamName"] = fromHuman.team.teamName;

		if (fromHuman.realHero !== undefined && is_valid(fromHuman.realHero))
			searchObject["realHero"] = fromHuman.realHero.toString();
		if (fromHuman.hasToothpick !== undefined && is_valid(fromHuman.hasToothpick))
			searchObject["hasToothpick"] = fromHuman.hasToothpick.toString();

		if (is_valid(fromHuman.coordinates?.x)) {
			// if two defined else...
			if (is_valid(toHuman.coordinates?.x)) {
				searchObject["x"] = [fromHuman.coordinates.x, toHuman.coordinates.x];
			} else {
				searchObject["x"] = fromHuman.coordinates.x;
			}
		}

		if (is_valid(fromHuman.coordinates?.y)) {
			// if two defined else...
			if (is_valid(toHuman.coordinates?.y)) {
				searchObject["y"] = [fromHuman.coordinates.y, toHuman.coordinates.y];
			} else {
				searchObject["y"] = fromHuman.coordinates.y;
			}
		}

		if (fromHuman.creationDate) {
			if (toHuman.creationDate) {
				searchObject["creationDate"] = [fromHuman.creationDate, toHuman.creationDate];
			} else {
				searchObject["creationDate"] = fromHuman.creationDate;
			}
		}

		if (fromHuman.impactSpeed) {
			if (toHuman.impactSpeed) {
				searchObject["impactSpeed"] = [fromHuman.impactSpeed, toHuman.impactSpeed];
			} else {
				searchObject["impactSpeed"] = fromHuman.impactSpeed;
			}
		}

		if (fromHuman.minutesOfWaiting) {
			if (toHuman.minutesOfWaiting) {
				searchObject["minutesOfWaiting"] = [fromHuman.minutesOfWaiting, toHuman.minutesOfWaiting];
			} else {
				searchObject["minutesOfWaiting"] = fromHuman.minutesOfWaiting;
			}
		}

		console.log(fromHuman);
		onTableUpdate({searchData: searchObject});
	}

	render() {

		const formFromFields = this.formFrom.generateFields();
		const formToFields = this.formTo.generateFields();

		return <div>
			<PaddedRow>
				{formFromFields["human-field-name"]()}
			</PaddedRow>

			<PaddedRow>
				{formFromFields["human-field-real-hero"]("col-md-6")}
				{formFromFields["human-field-has-toothpick"]("col-md-6")}
			</PaddedRow>

			<PaddedRow>
				{formFromFields["human-field-coordinates"]("col")}
				<InputRowSeparator/>
				{formToFields["human-field-coordinates"]("col", "form-label opacity-0")}
			</PaddedRow>

			{/* todo: creation date filtering */}
			{/*<PaddedRow>*/}
			{/*	{formFromFields["human-field-creation-date"]("col")}*/}
			{/*	<InputRowSeparator/>*/}
			{/*	{formToFields["human-field-creation-date"]("col", "form-label opacity-0")}*/}
			{/*</PaddedRow>*/}

			<PaddedRow>
				{formFromFields["human-field-impact-speed"]("col")}
				<InputRowSeparator/>
				{formToFields["human-field-impact-speed"]("col", "form-label opacity-0")}
			</PaddedRow>

			<PaddedRow>
				{formFromFields["human-field-minutes-of-waiting"]("col")}
				<InputRowSeparator/>
				{formToFields["human-field-minutes-of-waiting"]("col", "form-label opacity-0")}
			</PaddedRow>

			<PaddedRow>
				{formFromFields["human-field-weapon-type"]("col-md-6")}
				{formFromFields["human-field-mood"]("col-md-6")}
			</PaddedRow>

			<PaddedRow>
				{formFromFields["human-field-car"]("col-md-6")}
				{formFromFields["human-field-team"]("col-md-6")}
			</PaddedRow>

			<Button className={"loadable"}
			        onClick={() => this.processForm()}>
				Search
			</Button>
		</div>
	}
}

export default {
	Form
};

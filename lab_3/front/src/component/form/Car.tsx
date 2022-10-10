import React, {ChangeEvent} from "react"
import {ICar} from "../../model/Car";

type OnChangeEventType = {
	(event: ChangeEvent): void
}

function getForm(baseName: string = "",
                 car?: ICar,
                 onChange?: OnChangeEventType): JSX.Element {

	const carCoolValue = (car && car.cool) ? car.cool.toString() : "undefined";
	return <div className={"row"}>
		<div className="col-md-auto">
			<span className={"d-inline-block col-form-label"}>
				<label htmlFor={baseName + "car-field-cool"}
				       className={"form-check-label"}>Cool</label>
				<select id={baseName + "car-field-cool"}
				        name={baseName + "car-field-cool"}
				        className={"form-select"}
				        onChange={onChange}>
					<option value={"undefined"} selected={carCoolValue === "undefined"}>...</option>
					<option value={"false"} selected={carCoolValue === "false"}>False</option>
					<option value={"true"} selected={carCoolValue === "true"}>True</option>
				</select>
			</span>
		</div>
	</div>
}

export default {getForm}

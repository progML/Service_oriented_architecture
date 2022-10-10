import React, {ChangeEvent} from "react"
import {ICoordinates} from "../../model/Coordinates";

type OnChangeEventType = {
	(event: ChangeEvent): void
}

function getForm(baseName: string = "",
                 coordinates?: ICoordinates,
                 onChange?: OnChangeEventType): JSX.Element {
	return <div className={"row gap-3"}>
		<div className="col-md-auto">
			<div className={"row"}>
				<label htmlFor={baseName + "coordinates-field-x"} className={"col-sm-auto col-form-label"}>X</label>
				<div className={"col-sm-auto"}>
					<input id={baseName + "coordinates-field-x"}
					       name={baseName + "coordinates-field-x"}
					       className={"form-control"}
					       type={"number"}
					       value={coordinates?.x} onChange={onChange}
					       step={"any"}/>
				</div>
			</div>
		</div>
		<div className="col-md-auto">
			<div className={"row"}>
				<label htmlFor={baseName + "coordinates-field-y"} className={"col-sm-auto col-form-label"}>Y</label>
				<div className={"col-sm-auto"}>
					<input id={baseName + "coordinates-field-y"}
					       name={baseName + "coordinates-field-y"}
					       className={"form-control"}
					       type={"number"}
					       value={coordinates?.y}
					       onChange={onChange}
					       step={"any"}/>
				</div>
			</div>
		</div>
	</div>
}

export default {getForm}

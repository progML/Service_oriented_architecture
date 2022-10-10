import React, {ChangeEvent} from "react"
import {ITeam} from "../../model/Team";

type OnChangeEventType = {
	(event: ChangeEvent): void
}

function getForm(baseName: string = "",
                 team?: ITeam,
                 onChange?: OnChangeEventType): JSX.Element {
	return <div className={"row"}>
		<div className="col-md-auto">
			<div className={"row"}>
				<label htmlFor={baseName + "team-field-name"} className={"col-sm-auto col-form-label"}>Name</label>
				<div className={"col-sm-auto"}>
					<input id={baseName + "team-field-name"} name={baseName + "team-field-name"}
					       className={"form-control"} type={"text"}
					       value={team?.teamName} onChange={onChange}/>
				</div>
			</div>
		</div>
	</div>
}

export default {getForm}

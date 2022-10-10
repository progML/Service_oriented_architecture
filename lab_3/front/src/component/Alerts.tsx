import React from "react";
import {Alert} from "react-bootstrap";

interface AlertMessage {
	type: 'primary' | 'secondary' | 'success' | 'danger' | 'warning' | 'info' | 'light' | 'dark',
	message: string,
	show: boolean
}

interface AlertsProps {
	alerts: AlertMessage[],
	onHideAlert: { (id: number): void; }
}

function AlertBox(props: AlertsProps) {
	return <div className={"alert-box"}>
		{
			props.alerts.map((alert_message, index) => {
				return <Alert show={alert_message.show}
				              key={index}
				              variant={alert_message.type}
				              onClose={() => props.onHideAlert(index)}
				              dismissible>
					{alert_message.message}
				</Alert>
			})
		}
	</div>
}

export type {AlertMessage}
export {AlertBox}

import React from 'react';
import ReactDOM from 'react-dom';
import App from 'component/App';
import './css/bootstrap.min.css';
import './css/index.css';
import "react-datetime/css/react-datetime.css";

ReactDOM.render(
	<React.StrictMode>
		<App/>
	</React.StrictMode>,
	document.getElementById('root')
);

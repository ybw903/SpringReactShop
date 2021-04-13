import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './components/App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter as Router } from 'react-router-dom';
import { Provider, useDispatch } from 'react-redux';
import store from './store';
import { loadUser } from './service/user';
import { LoadUser } from './actions/auth';


  loadUser()?.then((user)=>{
    if(user) store.dispatch(LoadUser(user));
  }
  )

ReactDOM.render(
  
  <React.StrictMode>
    <Router>
      <Provider store={store}>
        <App />
      </Provider>
      
    </Router>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

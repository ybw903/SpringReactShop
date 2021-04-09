import React from 'react';
import { Redirect, Route, RouteProps } from 'react-router-dom';
import FobiddenPage from './FobiddenPage';

export interface ProtectedRouteProps extends RouteProps {
    component : any

}
  
const RouteIf = (props: ProtectedRouteProps)  => {
    const {component: Component, ...rest} = props;
    return (
      <Route
        {...rest}
        render={routeProps => 
            localStorage.getItem('authUser')?
            (<Component {...routeProps}/>):(<Redirect to='/403'/>)
        }
      />
    )
  }

export default RouteIf;
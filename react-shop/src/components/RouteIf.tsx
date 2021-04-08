import React from 'react';
import { Route, RouteProps } from 'react-router-dom';
import FobiddenPage from './FobiddenPage';

export interface ProtectedRouteProps extends RouteProps {
    component : any
    isAuthenticated: boolean
    isAllowed: boolean
    restrictedPath: string
    authenticationPath: string
}
  
const RouteIf = (props: ProtectedRouteProps)  => {
    const {component: Component, ...rest} = props;
    return (
      <Route
        {...rest}
        render={props => 
            localStorage.getItem('authUser')?(<Component {...props}/>):(<FobiddenPage/>)
        }
      />
    )
  }
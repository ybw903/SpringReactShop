import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Navigation from './Navigation';
import Products from './Products';

const App = () =>{
  return(
    <div>
      <header>
        <Navigation></Navigation>
      </header>
      <Switch>
        <Route path="/">
          <Products></Products>
        </Route>
      </Switch>
      
        
    </div>
  )
}

export default App;

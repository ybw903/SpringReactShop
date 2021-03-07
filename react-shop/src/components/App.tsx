import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Authentication from './Authentication';
import Cart from './Cart';
import Navigation from './Navigation';
import Products from './Products';

const App = () =>{
  return(
    <div>
      <header>
        <Navigation></Navigation>
      </header>
      
      <Switch>
        <Route exact path="/">
          <Products/>
        </Route>
        <Route path ="/cart">
          <Cart/>
        </Route>
        <Route path ="/auth">
          <Authentication/>
        </Route>
      </Switch>
      
        
    </div>
  )
}

export default App;

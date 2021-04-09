import React from 'react';
import { Route, Switch } from 'react-router-dom';
import Authentication from './Authentication';
import Cart from './Cart';
import Checkout from './Checkout';
import FobiddenPage from './FobiddenPage';
import Navigation from './Navigation';
import Products from './Products';
import RegistrationFrom from './RegistrationForm';
import RouteIf from './RouteIf';
import User from './User';

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
        <Route path = "/checkout">
          <Checkout/>
        </Route>
        <Route path ="/auth">
          <Authentication/>
        </Route>
        <Route path ="/registraition">
          <RegistrationFrom/>
        </Route>
        <Route path="/403">
          <FobiddenPage/>
        </Route>
        <RouteIf path ="/user" component={User}>
        </RouteIf>
      </Switch>
      
        
    </div>
  )
}

export default App;

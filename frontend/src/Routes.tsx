import { BrowserRouter, Switch, Route } from 'react-router-dom';
import Home from './pages/Home';
import Catalog from './pages/Catalog';
import NavBar from './components/NavBar';
import ProductDetails from './pages/ProductDetails';

const Routes = () => {
  return (
    <BrowserRouter>  
      <NavBar/>    
      <Switch>
        <Route path="/" exact>
          <Home />
        </Route>
        <Route path="/products" exact>
          <Catalog />
        </Route>
        <Route path="/products/:productId">
          <ProductDetails />
        </Route>
      </Switch>
    </BrowserRouter>
  );
};

export default Routes;

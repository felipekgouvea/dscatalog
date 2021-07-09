import NavBar from './NavBar';
import '../Admin/styles.css';
import { Route, Switch } from 'react-router-dom';

const Admin = () => {
  return (
    <div className="admin-container">
      <NavBar />
      <div className="admin-content">
        <Switch>
          <Route path="/admin/products">
            <h1>Product CRUD</h1>
          </Route>
          <Route path="/admin/categories">
            <h1>Category CRUD</h1>
          </Route>
          <Route path="/admin/users">
            <h1>User CRUD</h1>
          </Route>
        </Switch>
      </div>
    </div>
  );
};

export default Admin;

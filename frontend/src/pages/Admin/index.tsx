import NavBar from './NavBar';
import '../Admin/styles.css';
import { Switch } from 'react-router-dom';
import PrivateRoute from '../../components/PrivateRoute';
import Users from './User';

const Admin = () => {
  return (
    <div className="admin-container">
      <NavBar />
      <div className="admin-content">
        <Switch>
          <PrivateRoute path="/admin/products">
            <h1>Product CRUD</h1>
          </PrivateRoute>
          <PrivateRoute path="/admin/categories">
            <h1>Category CRUD</h1>
          </PrivateRoute>
          <PrivateRoute path="/admin/users">
            <Users/>
          </PrivateRoute>
        </Switch>
      </div>
    </div>
  );
};

export default Admin;

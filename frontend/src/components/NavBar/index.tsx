import './styles.css';
import 'bootstrap/js/src/collapse.js';

import { Link, NavLink } from 'react-router-dom';

const NavBar = () => {
  return (
    <nav className="navbar navbar-expand-md navbar-dark bg-primary main-nav">
      <div className="container-fluid">
        <a href="link">
          <Link to="/">
            <h4 className="nav-logo-text">DS Catalog</h4>
          </Link>
        </a>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#dscatalog-navbar"
          aria-controls="dscatalog-navbar"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="dscatalog-navbar">
          <ul className="navbar-nav offset-md-2 main-menu">
            <li>
              <a href="link" className="active">
                <NavLink to="/" activeClassName="active" exact>
                  HOME
                </NavLink>
              </a>
            </li>
            <li>
            <NavLink to="products" activeClassName="active">
              <a href="link">CATÁLOGO</a>
            </NavLink>
            </li>
            <li>
            <NavLink to="adm" activeClassName="active">
              <a href="link">ADMIN</a>
            </NavLink>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;

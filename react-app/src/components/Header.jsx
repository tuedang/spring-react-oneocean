import React from 'react';
import {Link} from "react-router-dom";

class Header extends React.Component {
    render() {
        return (
            <header>
                <nav className="navbar navbar-dark bg-dark mb-3">
                    <Link to={"/"} className="navbar-brand">
                        Heatmap
                    </Link>
                    <div className="navbar-expand mr-auto">
                        <div className="navbar-nav">
                            <Link to={"/"} className="nav-link">
                                Home
                            </Link>
                            <Link to={"/form"} className="nav-link">
                                Create Residence
                            </Link>
                        </div>
                    </div>
                </nav>
            </header>
        )
    }
}

export default Header;

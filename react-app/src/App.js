import React from 'react';
import './App.css'
import Home from './components/Home'
import Header from './components/Header'
import Form from "./components/Form";

import {BrowserRouter as Router, Switch, Route} from "react-router-dom";

function App() {
    return (
        <Router>
            <Header/>
            <main role="main" className="container">
                <Switch>
                    <Route path="/" component={Home} exact/>
                    <Route path="/form" component={Form}/>
                    <Route component={Error}/>
                </Switch>
            </main>
        </Router>
    );
}

export default App;

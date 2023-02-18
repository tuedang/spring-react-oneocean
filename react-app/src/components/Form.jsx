import React from 'react';
import axios from 'axios';
import MapContainer from './MapContainer';

class Form extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            cep: '',
            number: '',
            qtResidents: '',
            latitude: '',
            longitude: '',
            formErrors: {
                cep: '',
                number: '',
                qtResidents: '',
                latitude: '',
                longitude: '',
            },
            formValid: false,
            formSubmit: false,
            touched: {
                cep: false,
                number: false,
                qtResidents: false,
                latitude: false,
                longitude: false,
            }
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleMapClicked = this.handleMapClicked.bind(this);
        this.handleBlur = this.handleBlur.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    handleBlur = (field) => (evt) => {
        this.setState({
            touched: {...this.state.touched, [field]: true}
        });
    }

    handleInputChange(event) {
        event.preventDefault();
        const {name, value} = event.target;

        this.setState({
            [name]: value
        }, () => {
            this.validateField(name, value)
        })
    }

    validateField(fieldName, value) {
        let formErrors = this.state.formErrors;

        switch (fieldName) {
            case 'cep':
                formErrors.cep =
                    value.length !== 8
                        ? 'CEP must be 8 characters long!'
                        : '';
                break;
            case 'number':
                formErrors.number =
                    value === ''
                        ? 'Number is required!'
                        : '';
                break;
            case 'qtResidents':
                formErrors.qtResidents =
                    value === ''
                        ? 'Nº Residents is required!'
                        : '';
                formErrors.qtResidents =
                    Number(value) < 1
                        ? 'Nº Residents must be greater than 0!'
                        : '';
                break;
            case 'latitude':
                formErrors.latitude =
                    value === ''
                        ? 'Latitude is required!'
                        : '';
                break;
            case 'longitude':
                formErrors.longitude =
                    value === ''
                        ? 'Longitude is required!'
                        : '';
                break;
            default:
                break;
        }

        this.setState({errors: formErrors, [fieldName]: value});
    }

    validateForm = (errors) => {
        let valid = true;
        Object.values(errors).forEach(
            (val) => val.length > 0 && (valid = false)
        );
        return valid;
    }

    handleSubmit(e) {
        e.preventDefault();
        this.setState({formSubmit: true});

        for (const key in this.state.formErrors) {
            this.validateField(key, this.state[key]);
        }

        if (this.validateForm(this.state.formErrors)) {
            const postData = {
                "cep": this.state.cep,
                "number": this.state.number,
                "longitude": Number(this.state.longitude).toFixed(6),
                "latitude": Number(this.state.latitude).toFixed(6),
                "qtResidents": this.state.qtResidents,
            }

            axios.post('http://localhost:8080/api/residence', postData
            ).then(response => {
                if (response.status !== 200) {
                    console.log(response);
                }

                alert('Success');
            }).catch(err => console.error(err));
        } else {
            alert('Form not valid');
        }
    }

    handleMapClicked(e) {
        this.setState({longitude: e.lng, latitude: e.lat});
    }

    render() {
        return (
            <div className="container">
                <h1>Residence Form</h1>
                <div className="mb-5" style={{position: 'relative', 'paddingBottom': '56.25%'}}>
                    <MapContainer onClickedMap={this.handleMapClicked}/>
                </div>

                <span className="small">Click anywhere on the map to fill the form's latitude and longitude.</span>
                <form className="needs-validation" onSubmit={this.handleSubmit} noValidate>
                    <div className="form-row">
                        <div className="form-group col-md-6">
                            <label htmlFor="cep">CEP:</label>
                            <input id="cep" name="cep" type="number" value={this.state.cep}
                                   className={
                                       `form-control 
                                       ${this.state.formErrors.cep !== '' ? 'is-invalid' : ''}
                                       ${this.state.formErrors.cep === '' && this.state.touched.cep
                                       && this.state.cep.length > 1 ? 'is-valid' : ''} `}
                                   placeholder="00000-000"
                                   onChange={this.handleInputChange} onBlur={this.handleBlur('cep')}/>
                            <div className="invalid-feedback d-block">
                                {this.state.formErrors.cep}
                            </div>
                        </div>
                        <div className="form-group col-md-3">
                            <label htmlFor="number">Number:</label>
                            <input id="number" name="number" type="number" value={this.state.number}
                                   className={
                                       `form-control 
                                       ${this.state.formErrors.number !== '' ? 'is-invalid' : ''}
                                       ${this.state.formErrors.number === '' && this.state.touched.number ? 'is-valid' : ''} `}
                                   onChange={this.handleInputChange} onBlur={this.handleBlur('number')}/>
                            <div className="invalid-feedback d-block">
                                {this.state.formErrors.number}
                            </div>
                        </div>
                        <div className="form-group col-md-3">
                            <label htmlFor="qtResidents">Nº Residents:</label>
                            <input id="qtResidents" name="qtResidents" type="number" value={this.state.qtResidents}
                                   className={
                                       `form-control 
                                       ${this.state.formErrors.qtResidents !== '' ? 'is-invalid' : ''}
                                       ${this.state.formErrors.qtResidents === '' && this.state.touched.qtResidents ? 'is-valid' : ''} `}
                                   onChange={this.handleInputChange} onBlur={this.handleBlur('qtResidents')}/>
                            <div className="invalid-feedback d-block">
                                {this.state.formErrors.qtResidents}
                            </div>
                        </div>
                        <div className="form-group col-md-6">
                            <label htmlFor="latitude">Latitude:</label>
                            <input id="latitude" name="latitude" type="number" value={this.state.latitude}
                                   className={
                                       `form-control 
                                       ${this.state.formErrors.latitude !== '' ? 'is-invalid' : ''}
                                       ${this.state.formErrors.latitude === '' && this.state.touched.latitude ? 'is-valid' : ''} `}
                                   onChange={this.handleInputChange} onBlur={this.handleBlur('latitude')}/>
                            <div className="invalid-feedback d-block">
                                {this.state.formErrors.latitude}
                            </div>
                        </div>
                        <div className="form-group col-md-6">
                            <label htmlFor="longitude">Longitude:</label>
                            <input id="longitude" name="longitude" type="number" value={this.state.longitude}
                                   className={
                                       `form-control 
                                       ${this.state.formErrors.latitude !== '' ? 'is-invalid' : ''}
                                       ${this.state.formErrors.latitude === '' && this.state.touched.latitude ? 'is-valid' : ''} `}
                                   onChange={this.handleInputChange} onBlur={this.handleBlur('longitude')}/>
                            <div className="invalid-feedback d-block">
                                {this.state.formErrors.longitude}
                            </div>
                        </div>
                    </div>
                    <div className="form-row">
                        <button type="submit" className="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>
        )
    }
}

export default Form;

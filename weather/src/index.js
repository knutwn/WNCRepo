import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import "./index.css";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import partlycloudy_day from './images/clouds.png';
import fog from './images/fog.png';
import snow from './images/snow.svg'

const mountains = require("./fjell.json");

const currentMountainState = {
  id : "1"
};

const currMntStateContext = React.createContext(currentMountainState);
const dispatchCurrMntStateContext = React.createContext(undefined);

const useGlobalMntState = () => [
  React.useContext(currMntStateContext),
  React.useContext(dispatchCurrMntStateContext)
];

const CurrentMntStateProvider = ({ children }) => {
  const [state, dispatch] = React.useReducer(
    (state, newValue) => ({ ...state, ...newValue }),
    currentMountainState
  );

  return (
    <currMntStateContext.Provider value={state}>
      <dispatchCurrMntStateContext.Provider value={dispatch}>
        {children}
        </dispatchCurrMntStateContext.Provider>
    </currMntStateContext.Provider>
  );
}

function WeatherListItem(props) {
  const [wItems, setWItems] = useState([]);

var url =`https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=${props.lat}&lon=${props.lon}`;

  useEffect(() => {
    axios.get(url,
    {
      crossDomain: true,
      mode: 'no-cors',
      headers: {
        'Content-Type':'application/json'
      }
    }
  ).then(res => {
    const newWItems = res.data.properties.timeseries
      .filter(obj => obj.time.includes(props.dato))
      .map(obj => obj.data)
    setWItems(newWItems);
  })
}, [props.dato, url]);

  var wItem;
  if(wItems.length > 0) {
    wItem = <WeatherItem id={props.id} sted={props.sted} temp={wItems[0].instant.details.air_temperature} vind={wItems[0].instant.details.wind_speed}
    neste6timer={wItems[0].next_6_hours.summary.symbol_code} />;
  } else {
    wItem = <WeatherItem id={props.id} sted={props.sted} />;
  }

  return (
    <div>{wItem}</div>
  )
}

function WeatherItem(props) {
  var tempStyle;

  // eslint-disable-next-line
  const [state, dispatch] = useGlobalMntState();

  if(props.temp) {
    props.temp < -0 ? tempStyle = {color : 'Blue'} : tempStyle
     = { color : 'Red' };
  }

  var cloudImage;
  if(props.neste6timer === "partlycloudy_day") {
    cloudImage = partlycloudy_day;
  } else if(props.neste6timer === "fog") {
    cloudImage = fog;
  } else if(props.neste6timer === "lightsnow" || props.neste6timer === "lightsnowshowers_day"
          | props.neste6timer === "snow" || props.neste6timer === "snowshowers_day") {
    cloudImage = snow;
  }

  return (
    <div className="row" key={props.id}>
      <div className="col-md-5" onClick={() => dispatch({ id:props.id })}>{props.sted}</div>
      <div className="col-md-2" style={tempStyle}>{props.temp}</div>
      <div className="col-md-2">{props.vind}</div>
      <div className="col-md-1"><img src={cloudImage} height="30" width="30" alt=""/></div>
    </div>
  )
}

function WeatherList() {
  var dagensDato = new Date();
  var dag = String(dagensDato.getDate()).padStart(2,'0');
  var mnd = String(dagensDato.getMonth()+1).padStart(2,'0');
  var aar = dagensDato.getFullYear();

  var vaerDato = aar + "-" + mnd + "-" + dag;

  return (
    <CurrentMntStateProvider>
    <div>
      <div className="container">
        <h2>Fjellvær</h2>
        <div className="row">
            <div className="col-md-6">
              <div className="row" style={{ background: '#D3D3D3' }}>
                <div className="col-md-4">Sted</div>
                <div className="col-md-2">Temp</div>
                <div className="col-md-2">Vind(m/s)</div>
                <div className="col-md-3">Neste 6 timer</div>
              </div>
              {mountains.fjell.map(m => (
              <WeatherListItem id={m.id} sted={m.navn} dato={vaerDato} lat={m.lat} lon={m.lon}/>
              ))}
              </div>
              <div className="col-md-5">
                <div className="row" style={{ background: '#D3D3D3' }}>Fakta</div>
                <div className="row" style={{ background: '#f2f2f2' }}><MountainDetail /></div>
              </div>
          </div>
        </div>
    </div>
  </CurrentMntStateProvider>);
}

function MountainDetail() {

  // eslint-disable-next-line
  const [state, dispatch] = useGlobalMntState();

  return (
    <div>
      {mountains.fjell.filter(m => m.id === state.id)
        .map( m => (
          <div>
            <h3>{m.navn} ({m.hoyde}m)</h3>
            <p>Koordinater : {m.lat}, {m.lon}</p>
          </div>
        ))}
    </div>
  )
}


ReactDOM.render(<WeatherList/>, document.querySelector('#root'));

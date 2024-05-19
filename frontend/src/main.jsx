import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import 'bootstrap/dist/css/bootstrap.min.css'
import { GoogleOAuthProvider } from '@react-oauth/google';
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
      <GoogleOAuthProvider clientId="261703293557-0aa1tvmududgbosur0o3dleljfpsb90h.apps.googleusercontent.com">
    <App />
      </GoogleOAuthProvider>
  </React.StrictMode>,
)

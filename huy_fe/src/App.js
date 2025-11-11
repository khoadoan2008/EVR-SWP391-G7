import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css'; // For styling
import HomePage from './pages/HomePage'; // We'll create these files
import ServicesPage from './pages/ServicesPage';
import BookingPage from './pages/BookingPage';
import VehicleDetailsPage from './pages/VehicleDetailsPage';
import ConfirmationPage from './pages/ConfirmationPage';
import NewsPage from './pages/NewsPage';
import ContactPage from './pages/ContactPage';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import AdminDashboardPage from './pages/AdminDashboardPage';
import StaffDashboardPage from './pages/StaffDashboardPage';

function App() {
  return (
    <Router>
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
        <div className="container">
          <a className="navbar-brand" href="/">
            <img src="gf-logo.png" alt="GF Logo" style={{ width: 50 }} /> {/* Add your logo image */}
          </a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav me-auto">
              <li className="nav-item"><Link className="nav-link" to="/">Dịch vụ</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/used-cars">Mua xe cũ chính hãng</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/limo">Limo Green</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/intro">Giới thiệu</Link></li>
              <li className="nav-item"><Link className="nav-link" to="/news">Tin tức</Link></li>
            </ul>
            <button className="btn btn-outline-light">Đăng nhập</button>
          </div>
        </div>
      </nav>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/services" element={<ServicesPage />} />
        <Route path="/booking" element={<BookingPage />} />
        <Route path="/vehicle-details" element={<VehicleDetailsPage />} />
        <Route path="/confirmation" element={<ConfirmationPage />} />
        <Route path="/news" element={<NewsPage />} />
        <Route path="/contact" element={<ContactPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/admin-dashboard" element={<AdminDashboardPage />} />
        <Route path="/staff-dashboard" element={<StaffDashboardPage />} />
      </Routes>
    </Router>
  );
}

export default App;
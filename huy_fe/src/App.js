import React, { useState } from 'react';
import './App.css';

function App() {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
    age: '',
    license: ''
  });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // BR-03: Age validation
    if (formData.age < 18) {
      setError('Must be 18+ to register');
      return;
    }
    if (!formData.license) {
      setError('License number required');
      return;
    }
    setError('');
    alert(`Registered: ${formData.email}`);
  };

  return (
    <div className="App" style={{ padding: '20px', maxWidth: '400px', margin: '0 auto' }}>
      <h1>EV Rental - Register</h1>
      <form onSubmit={handleSubmit}>
        <input
          name="email"
          type="email"
          placeholder="Email"
          value={formData.email}
          onChange={handleChange}
          required
          style={{ display: 'block', margin: '10px 0', padding: '10px', width: '100%' }}
        />
        <input
          name="password"
          type="password"
          placeholder="Password"
          value={formData.password}
          onChange={handleChange}
          required
          style={{ display: 'block', margin: '10px 0', padding: '10px', width: '100%' }}
        />
        <input
          name="age"
          type="number"
          placeholder="Age"
          value={formData.age}
          onChange={handleChange}
          required
          style={{ display: 'block', margin: '10px 0', padding: '10px', width: '100%' }}
        />
        <input
          name="license"
          placeholder="Driver License Number"
          value={formData.license}
          onChange={handleChange}
          required
          style={{ display: 'block', margin: '10px 0', padding: '10px', width: '100%' }}
        />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button 
          type="submit" 
          style={{ 
            padding: '10px 20px', 
            background: '#007bff', 
            color: 'white', 
            border: 'none', 
            width: '100%' 
          }}
        >
          Register
        </button>
      </form>
    </div>
  );
}

export default App;
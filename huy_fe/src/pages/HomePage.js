import React from 'react';
import { Button, Container } from 'react-bootstrap';

const HomePage = () => (
  <div style={{ backgroundColor: '#000', color: '#fff', minHeight: '100vh' }}>
    <Container className="text-center py-5">
      <h1 style={{ fontSize: '3rem', color: '#00ff00' }}>Đáp Ứng Moi Nhu Cầu Thuê Xe</h1>
      <p style={{ fontSize: '1.2rem' }}>Cung cấp dịch vụ thuê xe tự lái và có tài xế, phục vụ mọi nhu cầu di chuyển của bạn</p>
      <Button variant="success" className="me-2">Thuê xe có tài</Button>
      <Button variant="light">Thuê xe tự lái</Button>
    </Container>
    {/* Add background image in CSS or inline */}
    <style jsx>{`
      div { background-image: url('ev-garage.jpg'); background-size: cover; }
    `}</style>
  </div>
);

export default HomePage;
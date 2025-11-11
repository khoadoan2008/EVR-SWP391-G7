import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';

const AdminDashboardPage = () => (
  <Container className="py-5">
    <h2>Admin Dashboard</h2>
    <Row>
      <Col md={6}><h3>Fleet Status</h3><p>10 Available</p></Col>
      <Col md={6}><h3>Reports</h3><p>Revenue: 50M VND</p></Col>
    </Row>
  </Container>
);

export default AdminDashboardPage;
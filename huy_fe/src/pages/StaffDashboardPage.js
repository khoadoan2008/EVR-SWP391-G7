import React from 'react';
import { Container, ListGroup } from 'react-bootstrap';

const StaffDashboardPage = () => (
  <Container className="py-5">
    <h2>Staff Dashboard</h2>
    <ListGroup>
      <ListGroup.Item>Pending Handovers: 3</ListGroup.Item>
      <ListGroup.Item>Late Returns: 1</ListGroup.Item>
    </ListGroup>
  </Container>
);

export default StaffDashboardPage;
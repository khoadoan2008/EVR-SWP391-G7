import React from 'react';
import { Table, Container } from 'react-bootstrap';

const DashboardPage = () => (
  <Container className="py-5">
    <h2>Dashboard</h2>
    <Table striped bordered hover>
      <thead>
        <tr><th>Booking</th><th>Date</th><th>Status</th></tr>
      </thead>
      <tbody>
        <tr><td>VF9</td><td>Nov 10</td><td>Confirmed</td></tr>
      </tbody>
    </Table>
  </Container>
);

export default DashboardPage;
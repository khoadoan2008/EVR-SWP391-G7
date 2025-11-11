import React from 'react';
import { Table, Button, Container } from 'react-bootstrap';

const ConfirmationPage = () => (
  <Container className="py-5">
    <h2>Booking Confirmed</h2>
    <Table striped bordered hover>
      <tbody>
        <tr><td>Vehicle</td><td>VinFast VF9</td></tr>
        <tr><td>Dates</td><td>Nov 10-12</td></tr>
        <tr><td>Total</td><td>5,000,000 VND</td></tr>
      </tbody>
    </Table>
    <img src="qr-code.png" alt="QR for Check-In" style={{ width: 200 }} />
    <Button variant="success">Download Receipt</Button>
  </Container>
);

export default ConfirmationPage;
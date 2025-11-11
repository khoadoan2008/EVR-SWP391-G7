import React from 'react';
import { Image, Button, Container } from 'react-bootstrap';

const VehicleDetailsPage = () => (
  <Container className="py-5">
    <Image src="vinfast-vf9.jpg" fluid />
    <h3>VinFast VF9 Plus</h3>
    <p>Battery: 80%, Price: 2,500,000 VND/day, Range: 423km (WLTP)</p>
    <Button variant="success">Book Now</Button>
  </Container>
);

export default VehicleDetailsPage;
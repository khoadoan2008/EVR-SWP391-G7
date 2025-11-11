import React from 'react';
import { Card, Row, Col, Container, Button } from 'react-bootstrap'; // Added Button

const ServicesPage = () => (
  <Container className="py-5">
    <h2>Dịch Vụ Thuê Xe</h2>
    <Row>
      <Col md={4}>
        <Card>
          <Card.Img variant="top" src="self-drive.jpg" />
          <Card.Body>
            <Card.Title>Thuê Xe Tự Lái</Card.Title>
            <Card.Text>Thuê xe điện linh hoạt theo ngày/giờ.</Card.Text>
            <Button variant="success">Chi Tiết</Button>
          </Card.Body>
        </Card>
      </Col>
      <Col md={4}>
        <Card>
          <Card.Img variant="top" src="chauffeured.jpg" />
          <Card.Body>
            <Card.Title>Thuê Xe Có Tài Xế</Card.Title>
            <Card.Text>Dịch vụ cao cấp với tài xế chuyên nghiệp.</Card.Text>
            <Button variant="success">Chi Tiết</Button>
          </Card.Body>
        </Card>
      </Col>
      {/* Add Limo Green, etc. */}
    </Row>
  </Container>
);

export default ServicesPage;
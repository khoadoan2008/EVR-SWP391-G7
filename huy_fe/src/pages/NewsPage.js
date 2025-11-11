import React from 'react';
import { Card, Row, Col, Container, Button } from 'react-bootstrap'; // Added Button

const NewsPage = () => (
  <Container className="py-5">
    <h2>Tin Tức</h2>
    <Row>
      <Col md={4}>
        <Card>
          <Card.Img variant="top" src="news-thumbnail.jpg" />
          <Card.Body>
            <Card.Title>Cách Thuê Xe Điện VinFast</Card.Title>
            <Card.Text>3 steps simple...</Card.Text>
            <Button variant="success">Read More</Button>
          </Card.Body>
        </Card>
      </Col>
      {/* Add more articles */}
    </Row>
  </Container>
);

export default NewsPage;
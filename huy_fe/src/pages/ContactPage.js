import React, { useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap';

const ContactPage = () => {
  const [message, setMessage] = useState('');

  return (
    <Container className="py-5">
      <h2>Liên Hệ</h2>
      <Form>
        <Form.Group>
          <Form.Label>Email</Form.Label>
          <Form.Control type="email" />
        </Form.Group>
        <Form.Group>
          <Form.Label>Message</Form.Label>
          <Form.Control as="textarea" value={message} onChange={(e) => setMessage(e.target.value)} />
        </Form.Group>
        <Button variant="success" type="submit">Gửi</Button>
      </Form>
    </Container>
  );
};

export default ContactPage;
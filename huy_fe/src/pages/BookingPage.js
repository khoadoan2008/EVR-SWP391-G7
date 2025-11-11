import React, { useState } from 'react';
import { Form, Button, Container } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

const BookingPage = () => {
  const [startDate, setStartDate] = useState(new Date());
  const [duration, setDuration] = useState(1);
  const price = duration * 2300000; // Mock per BR-09

  return (
    <Container className="py-5">
      <h2>Đặt Thuê Xe</h2>
      <Form>
        <Form.Group>
          <Form.Label>Ngày Thuê</Form.Label>
          <DatePicker selected={startDate} onChange={(date) => setStartDate(date)} />
        </Form.Group>
        <Form.Group>
          <Form.Label>Thời Gian (Ngày)</Form.Label>
          <Form.Control type="number" value={duration} onChange={(e) => setDuration(e.target.value)} min={1} />
        </Form.Group>
        <p>Giá: {price} VND (Deposit: {price * 5})</p>
        <Button variant="success" type="submit">Xác Nhận</Button>
      </Form>
    </Container>
  );
};

export default BookingPage;
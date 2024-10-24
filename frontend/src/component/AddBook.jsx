import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AddBook() {
  const [book, setBook] = useState({ bookTitle: '', bookAuthor: '', availability: true });
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook(prevBook => ({
      ...prevBook,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8008/api/books/addNewBook', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(book),
      });

      if (!response.ok) {
        throw new Error('Failed to add book');
      }

      const data = await response.json();
      setSuccess('Book added successfully!');
      setError('');
      setBook({ bookTitle: '', bookAuthor: '', availability: true });
    } catch (error) {
      setError('Error adding book: ' + error.message);
      setSuccess('');
    }
  };

  return (
    <div className="container mx-auto mt-8">
      <h2 className="text-2xl font-bold mb-4">Add New Book</h2>
      {error && <p className="text-red-500 mb-4">{error}</p>}
      {success && <p className="text-green-500 mb-4">{success}</p>}
      <form onSubmit={handleSubmit} className="max-w-md">
        <div className="mb-4">
          <label htmlFor="bookTitle" className="block mb-2">Title:</label>
          <input
            type="text"
            id="bookTitle"
            name="bookTitle"
            value={book.bookTitle}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border rounded"
          />
        </div>
        <div className="mb-4">
          <label htmlFor="bookAuthor" className="block mb-2">Author:</label>
          <input
            type="text"
            id="bookAuthor"
            name="bookAuthor"
            value={book.bookAuthor}
            onChange={handleChange}
            required
            className="w-full px-3 py-2 border rounded"
          />
        </div>
        <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600">
          Add Book
        </button>
      </form>
    </div>
  );
}

export default AddBook;

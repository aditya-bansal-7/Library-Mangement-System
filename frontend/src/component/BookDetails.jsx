import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function BookDetails() {
  const { bookId } = useParams();
  const [book, setBook] = useState(null);
  const navigate = useNavigate();
  const isAdmin = true; // Replace with actual admin check

  useEffect(() => {
    const fetchBookDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/books/book/${bookId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch book details');
        }
        const data = await response.json();
        setBook(data);
      } catch (error) {
        console.error('Error fetching book details:', error);
      }
    };

    fetchBookDetails();
  }, [bookId]);

  if (!book) {
    return <div>Loading...</div>;
  }

  const handleEdit = () => {
    navigate(`/admin/editbook/${bookId}`);
  };

  return (
    <div className="container mx-auto mt-8">
      <h2 className="text-2xl font-bold mb-4">{book.bookTitle}</h2>
      <p><strong>Author:</strong> {book.bookAuthor}</p>
      <p><strong>Description:</strong> {book.description}</p>
      <p><strong>Tags:</strong> {book.tags}</p>
      <h3 className="text-xl font-bold mt-4">Reviews</h3>
      <ul>
        {book.reviews.map(review => (
          <li key={review.reviewId}>
            <p>{review.message}</p>
            <p><small>User ID: {review.userId}</small></p>
          </li>
        ))}
      </ul>
      {isAdmin && (
        <button onClick={handleEdit} className="bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600 mt-4">
          Edit Book
        </button>
      )}
    </div>
  );
}

export default BookDetails;

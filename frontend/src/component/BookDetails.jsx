import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function BookDetails() {
  const { bookId } = useParams();
  const [book, setBook] = useState(null);
  const [newReview, setNewReview] = useState('');
  const navigate = useNavigate();
  const [currentPage, setCurrentPage] = useState(1);
  const reviewsPerPage = 5;
  const user = localStorage.getItem('user') ? JSON.parse(localStorage.getItem('user')) : null;

  const isAdmin = user ? user.admin : false;

  useEffect(() => {
    const fetchBookDetails = async () => {
      try {
        const response = await fetch(`http://localhost:8008/api/books/book/${bookId}`);
        if (!response.ok) {
          throw new Error('Failed to fetch book details');
        }
        const data = await response.json();
        data.reviews = data.reviews || []; // Ensure reviews is an array
        setBook(data);
      } catch (error) {
        console.error('Error fetching book details:', error);
      }
    };

    fetchBookDetails();
  }, [bookId]);

  const handleEdit = () => {
    navigate(`/admin/editbook/${bookId}`);
  };
  
  const handleUserClick = (userId) => {
    navigate(`/profile/${userId}`);
  };

  // Conditionally render current reviews if book and reviews are available
  const currentReviews = book ? book.reviews.slice((currentPage - 1) * reviewsPerPage, currentPage * reviewsPerPage) : [];

  // Total number of pages
  const totalPages = book ? Math.ceil(book.reviews.length / reviewsPerPage) : 1;

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleAddReview = async (e) => {
    e.preventDefault();
    if (!newReview) return;

    try {
      const response = await fetch(`http://localhost:8008/api/books/book/${bookId}/addReview`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: newReview, userId: user.userId, userName: user.firstName + " " + user.lastName }),
      });

      if (response.ok) {
        const updatedBook = await response.json();
        setBook(updatedBook);
        setNewReview('');
        setCurrentPage(1); // Reset to the first page after adding a review
      } else {
        throw new Error('Failed to add review');
      }
    } catch (error) {
      console.error('Error adding review:', error);
    }
  };

  if (!book) {
    return <div className="flex items-center justify-center h-screen text-lg font-semibold">Loading...</div>;
  }

  return (
    <div className="container mx-auto mt-8 p-6 bg-gray-50 shadow-lg rounded-lg max-w-2xl">
      <h2 className="text-4xl font-bold text-gray-800 mb-4">{book.bookTitle}</h2>
      <p className="text-lg text-gray-700 mb-2"><strong>Author:</strong> {book.bookAuthor}</p>
      <p className="text-lg text-gray-700 mb-2"><strong>Description:</strong> {book.description}</p>
      <p className="text-lg text-gray-700 mb-2"><strong>Tags:</strong> {book.tags}</p>
      <p className="text-lg text-gray-700 mb-4"><strong>Availability:</strong> {book.availability ? 'Available' : 'Not Available'}</p>
      
      {isAdmin && (
        <button
          onClick={handleEdit}
          className="bg-indigo-600 text-white px-6 py-3 rounded hover:bg-indigo-700 transition duration-200 mt-4"
        >
          Edit Book
        </button>
      )}
      
      <h3 className="text-2xl font-bold mt-6 mb-2 text-gray-800">Reviews</h3>
      <ul className="mb-4 space-y-4">
        {currentReviews.map(review => (
          <li key={review.reviewId} className="bg-white p-4 rounded-lg shadow-md hover:shadow-lg transition-shadow duration-200">
            <p className="text-gray-800">{review.message}</p>
            <p className="text-gray-600 text-sm">
              <small>
                User: <button
                  onClick={() => handleUserClick(review.userId)}
                  className="text-indigo-500 hover:underline"
                >
                  {review.userName}
                </button>
              </small>
            </p>
          </li>
        ))}
      </ul>

      {/* Pagination Controls */}
      <div className="flex justify-between items-center mt-4">
        <button
          onClick={handlePrevPage}
          disabled={currentPage === 1}
          className={`px-4 py-2 rounded ${currentPage === 1 ? 'bg-gray-300' : 'bg-indigo-600 text-white hover:bg-indigo-700 transition duration-200'}`}
        >
          Previous
        </button>
        <span className="text-gray-700 font-semibold">
          Page {currentPage} of {totalPages}
        </span>
        <button
          onClick={handleNextPage}
          disabled={currentPage === totalPages}
          className={`px-4 py-2 rounded ${currentPage === totalPages ? 'bg-gray-300' : 'bg-indigo-600 text-white hover:bg-indigo-700 transition duration-200'}`}
        >
          Next
        </button>
      </div>

      {/* Display review input field if user is logged in and not an admin */}
      {user && !isAdmin && (
        <form onSubmit={handleAddReview} className="mt-6">
          <textarea
            value={newReview}
            onChange={(e) => setNewReview(e.target.value)}
            placeholder="Write your review..."
            className="w-full p-4 rounded border border-gray-300 focus:outline-none focus:ring-2 focus:ring-indigo-500"
            rows="4"
          />
          <button
            type="submit"
            className="bg-indigo-600 text-white px-4 py-2 rounded mt-2 hover:bg-indigo-700 transition duration-200"
          >
            Submit Review
          </button>
        </form>
      )}
    </div>
  );
}

export default BookDetails;
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const BookManagement = () => {
    const [books, setBooks] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [loading, setLoading] = useState(true);
    const [deleting, setDeleting] = useState(null); // Track book being deleted

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        setLoading(true);
        try {
            const response = await axios.get('http://localhost:8008/api/books/getAllBooks');
            setBooks(response.data);
        } catch (error) {
            console.error('Error fetching books:', error);
        }
        setLoading(false);
    };

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    const handleDeleteBook = async (bookId) => {
        if (!window.confirm("Are you sure you want to delete this book?")) return;
        setDeleting(bookId);
        try {
            await axios.delete(`http://localhost:8008/api/books/deleteBook/${bookId}`);
            setBooks(books.filter(book => book.bookId !== bookId));
        } catch (error) {
            console.error('Error deleting book:', error);
        }
        setDeleting(null);
    };

    const handleToggleAvailability = async (bookId, currentAvailability) => {
        try {
            await axios.post(`http://localhost:8008/api/books/${bookId}/toggleAvailability`, {
                availability: !currentAvailability
            });
            fetchBooks(); // Refresh the book list after toggling
        } catch (error) {
            console.error('Error toggling availability:', error);
        }
    };

    const filteredBooks = books.filter(book =>
        book.bookTitle.toLowerCase().includes(searchTerm.toLowerCase()) ||
        book.bookAuthor.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div className="p-6 max-w-5xl mx-auto">
            <h2 className="text-3xl font-extrabold mb-6 text-gray-800">Book Management</h2>
            
            <Link
                to="/admin/addbook"
                className="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700 transition duration-200 ease-in-out mb-6 inline-block"
            >
                + Add New Book
            </Link>
            
            <input
                type="text"
                placeholder="Search books..."
                value={searchTerm}
                onChange={handleSearch}
                className="w-full p-3 border border-gray-300 rounded-lg shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-400 mb-4"
            />

            {loading ? (
                <p className="text-center text-gray-500">Loading books...</p>
            ) : (
                <table className="w-full text-left border-collapse mt-4">
                    <thead>
                        <tr className="bg-gray-100">
                            <th className="border p-4 text-gray-600">Title</th>
                            <th className="border p-4 text-gray-600">Author</th>
                            <th className="border p-4 text-gray-600">Availability</th>
                            <th className="border p-4 text-gray-600">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredBooks.map(book => (
                            <tr key={book.bookId} className="hover:bg-gray-50">
                                <td className="border p-4">{book.bookTitle}</td>
                                <td className="border p-4">{book.bookAuthor}</td>
                                <td className="border p-4">
                                    <span className={`px-2 py-1 rounded ${book.availability ? 'bg-green-100 text-green-600' : 'bg-red-100 text-red-600'}`}>
                                        {book.availability ? 'Available' : 'Not Available'}
                                    </span>
                                    <button
                                        onClick={() => handleToggleAvailability(book.bookId, book.availability)}
                                        className="text-blue-600 hover:underline ml-4"
                                    >
                                        {book.availability ? 'Mark as Unavailable' : 'Mark as Available'}
                                    </button>
                                </td>
                                <td className="border p-4">
                                    <Link
                                        to={`/admin/editbook/${book.bookId}`}
                                        className="text-blue-600 hover:underline mr-4"
                                    >
                                        Edit
                                    </Link>
                                    <button
                                        onClick={() => handleDeleteBook(book.bookId)}
                                        className="text-red-600 hover:underline"
                                        disabled={deleting === book.bookId}
                                    >
                                        {deleting === book.bookId ? 'Deleting...' : 'Delete'}
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}

            {filteredBooks.length === 0 && !loading && (
                <p className="text-center text-gray-500 mt-6">No books found</p>
            )}
        </div>
    );
};

export default BookManagement;

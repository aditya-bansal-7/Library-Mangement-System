import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const BookManagement = () => {
    const [books, setBooks] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        try {
            const response = await axios.get('http://localhost:8008/api/books/getAllBooks');
            setBooks(response.data);
        } catch (error) {
            console.error('Error fetching books:', error);
        }
    };

    const handleSearch = (e) => {
        setSearchTerm(e.target.value);
    };

    const filteredBooks = books.filter(book =>
        book.bookTitle.toLowerCase().includes(searchTerm.toLowerCase()) ||
        book.bookAuthor.toLowerCase().includes(searchTerm.toLowerCase())
    );

    return (
        <div>
            <h2 className="text-2xl font-bold mb-4">Book Management</h2>
            <Link to="/admin/addbook" className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 mb-4 inline-block">
                Add New Book
            </Link>
            <input
                type="text"
                placeholder="Search books..."
                value={searchTerm}
                onChange={handleSearch}
                className="w-full p-2 border rounded mb-4"
            />
            <table className="w-full border-collapse">
                <thead>
                    <tr className="bg-gray-200">
                        <th className="border p-2">Title</th>
                        <th className="border p-2">Author</th>
                        <th className="border p-2">Availability</th>
                        <th className="border p-2">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredBooks.map(book => (
                        <tr key={book.bookId}>
                            <td className="border p-2">{book.bookTitle}</td>
                            <td className="border p-2">{book.bookAuthor}</td>
                            <td className="border p-2">{book.availability ? 'Available' : 'Not Available'}</td>
                            <td className="border p-2">
                                <Link to={`/admin/editbook/${book.bookId}`} className="text-blue-500 hover:underline mr-2">
                                    Edit
                                </Link>
                                <button onClick={() => handleDeleteBook(book.bookId)} className="text-red-500 hover:underline">
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default BookManagement;

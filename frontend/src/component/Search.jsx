import React, { useState, useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

const Search = () => {
  const [searchParams] = useSearchParams();
  const [searchResults, setSearchResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const searchTerm = searchParams.get('q');
    if (searchTerm) {
      searchBooks(searchTerm);
    } else {
      setSearchResults([]);
    }
  }, [searchParams]);

  const searchBooks = async (term) => {
    setLoading(true);
    setError(null);
    try {
      // Replace this with your actual API endpoint
      const response = await fetch(`/api/books/search?q=${encodeURIComponent(term)}`);
      if (!response.ok) {
        throw new Error('Failed to fetch search results');
      }
      const data = await response.json();
      setSearchResults(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container mx-auto mt-8">
      <h2 className="text-2xl font-bold mb-4">Search Results</h2>
      {loading && <p>Loading...</p>}
      {error && <p className="text-red-500">Error: {error}</p>}
      {searchResults.length > 0 ? (
        <ul className="space-y-4">
          {searchResults.map((book) => (
            <li key={book.bookId} className="border p-4 rounded">
              <h3 className="font-bold">{book.bookTitle}</h3>
              <p>Author: {book.bookAuthor}</p>
              <p>Available: {book.availability ? 'Yes' : 'No'}</p>
            </li>
          ))}
        </ul>
      ) : (
        <p>No results found.</p>
      )}
    </div>
  );
};

export default Search;

import { useState, useEffect } from 'react'
import { NavLink, Outlet, useNavigate } from 'react-router-dom'
import './App.css'

function App() {
  const [user, setUser] = useState(null);
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const storedUser = localStorage.getItem('user');
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error('Error parsing user data:', error);
        localStorage.removeItem('user'); // Remove invalid data
      }
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('user');
    setUser(null);
    navigate('/');
  };

  const handleSearch = async (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    
    if (query.trim() === '') {
      setSearchResults([]);
      return;
    }

    try {
      console.log('Sending search request:', { user: user ? user.id : null, query: query });
      const response = await fetch('http://localhost:8008/api/books/search', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          user: user ? user.id : null,
          query: query
        }),
      });
      console.log('Search response status:', response.status);
      if (response.ok) {
        const data = await response.json();
        console.log('Search results:', data);
        setSearchResults(data);
      } else {
        console.error('Error fetching search results');
      }
    } catch (error) {
      console.error('Error:', error);
    }
  };

  const navLinkClass = ({ isActive }) => 
    isActive ? "text-yellow-300 font-bold" : "text-white hover:text-gray-300";

  return (
    <>
      <nav className="bg-gray-800 p-4">
        <div className="container mx-auto flex justify-between items-center">
          <div className="text-white font-bold text-xl">Library Management System</div>
          <div className="flex space-x-4 items-center">
            <NavLink to="/" className={navLinkClass} end>Home</NavLink>
            <NavLink to="/about" className={navLinkClass}>About</NavLink>
            <NavLink to="/categories" className={navLinkClass}>Categories</NavLink>
            <div className="relative">
              <input
                type="text"
                placeholder="Search books..."
                value={searchQuery}
                onChange={handleSearch}
                className="px-2 py-1 rounded text-black"
              />
              {searchResults.length > 0 && (
                <div className="absolute z-10 mt-1 w-full bg-white rounded-md shadow-lg max-h-60 overflow-y-auto">
                   {searchResults.map((book) => (
                    <div key={book.bookId} className="px-4 py-2 hover:bg-gray-100 cursor-pointer">
                      <div className="font-semibold">{book.bookTitle}</div>
                      <div className="text-sm text-gray-600">{book.bookAuthor}</div>
                      <div className="text-xs text-gray-500">
                        {book.availability ? 'Available' : 'Not Available'}
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
          <div className="flex space-x-2">
            {user ? (
              <>
                <NavLink to="/profile" className="bg-purple-500 hover:bg-purple-600 text-white px-3 py-1 rounded">
                  My Profile
                </NavLink>
                <button onClick={handleLogout} className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded">
                  Logout
                </button>
              </>
            ) : (
              <>
                <NavLink to="/login" className={({ isActive }) => 
                  isActive ? "bg-blue-600 text-white px-3 py-1 rounded" : "bg-blue-500 hover:bg-blue-600 text-white px-3 py-1 rounded"
                }>Login</NavLink>
                <NavLink to="/signup" className={({ isActive }) => 
                  isActive ? "bg-green-600 text-white px-3 py-1 rounded" : "bg-green-500 hover:bg-green-600 text-white px-3 py-1 rounded"
                }>Sign Up</NavLink>
              </>
            )}
          </div>
        </div>
      </nav>
      <Outlet />
    </>
  )
}

export default App

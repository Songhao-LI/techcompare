import React, { useState, useEffect } from 'react';
import AOS from 'aos';
import { useLocation } from 'react-router-dom';
import axios from 'axios';
import Fuse from 'fuse.js';

const SearchResult = () => {
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(true);
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const query = queryParams.get('query');

  useEffect(() => {
    AOS.init({
      duration: 800,
      easing: 'ease-in-sine',
      delay: 100,
      offset: 100,
    });
    AOS.refresh();

    const fetchData = async () => {
      try {
        const response = await axios.get(`/api/products?q=${encodeURIComponent(query)}`);
        const fuse = new Fuse(response.data, {
          keys: ['title', 'description'], // fields to index for searching
          includeScore: true,
          threshold: 0.8
        });
        const result = fuse.search(query);
        setProducts(result.map(item => item.item));
      } catch (error) {
        console.error('Failed to fetch products', error);
      }
      setLoading(false);
    };
    fetchData().then(() => {
    });
  }, [query]);  // 依赖项是 query，这样当查询参数变化时，会重新获取数据

  const highlightMatch = (text, query) => {
    const regex = new RegExp(`(${query})`, 'gi');
    const parts = text.split(regex);
    return parts.map((part, index) =>
      regex.test(part) ? <span key={index} className="text-red-500">{part}</span> : part
    );
  };

  return (
    <div className="bg-gray-100 dark:bg-gray-900 pt-20 min-h-screen">
      <div className="dark:text-slate-500">
        <h1 className="text-center text-2xl font-bold dark:text-white text-black">Search Result for: {query}</h1>
      </div>
      {loading ? (
        <p>Loading...</p>
      ) : (
        <section className="py-10">
          <div className="mx-auto max-w-6xl p-6">
            <ul>
              {products.map(product => (
                <li key={product.id} className="dark:shadow-cyan-500/50 shadow-xl mb-4 p-3 dark:bg-gray-500 bg-white shadow-lg hover:shadow-xl rounded-xl hover:transform hover:scale-105 duration-300">
                  <a href="#">
                    <div className="flex items-center space-x-4">
                      <img src={product.image} alt={product.title} className="h-20 w-20 object-cover rounded-xl" />
                      <div className="flex flex-col">
                        <h2 className="text-lg font-bold text-slate-700 dark:text-black">{highlightMatch(product.title, query)}</h2>
                        <p className="text-sm text-slate-400 dark:text-slate-700">{highlightMatch(product.description, query)}</p>
                        <p className="mt-2 text-blue-500 dark:text-blue-800 font-bold">${product.price}</p>
                      </div>
                    </div>
                  </a>
                </li>
              ))}
            </ul>
          </div>
        </section>
      )}
    </div>
  );
};

export default SearchResult;

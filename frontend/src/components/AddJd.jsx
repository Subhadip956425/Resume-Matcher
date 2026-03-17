import { useState } from "react";
import api from "../api/axios";

export default function AddJd({ onAdded }) {
  const [role, setRole] = useState("");
  const [jdText, setJdText] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!role.trim() || !jdText.trim()) return;

    setLoading(true);
    setSuccess(false);

    try {
      await api.post("/jd", { role, jdText });
      setSuccess(true);
      setRole("");
      setJdText("");
      if (onAdded) onAdded();
    } catch (err) {
      console.error("Failed to add JD:", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm w-full max-w-2xl mx-auto">
      <h2 className="text-lg font-semibold text-gray-800 mb-4">
        Add Job Description
      </h2>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            Role Title
          </label>
          <input
            type="text"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            placeholder="e.g. Backend Developer"
            className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400"
          />
        </div>

        <div>
          <label className="block text-sm font-medium text-gray-600 mb-1">
            JD Text
          </label>
          <textarea
            value={jdText}
            onChange={(e) => setJdText(e.target.value)}
            rows={6}
            placeholder="Paste the full job description here..."
            className="w-full border border-gray-300 rounded-lg px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 resize-none"
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-gray-800 hover:bg-gray-900 disabled:bg-gray-400 text-white font-medium py-2.5 rounded-lg transition-colors text-sm"
        >
          {loading ? "Saving..." : "Add JD"}
        </button>

        {success && (
          <p className="text-green-600 text-sm text-center">
            JD added successfully.
          </p>
        )}
      </form>
    </div>
  );
}

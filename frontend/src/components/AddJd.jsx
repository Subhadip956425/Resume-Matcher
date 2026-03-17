import { useState } from "react";
import api from "../api/axios";

export default function AddJd({ onAdded }) {
  const [role, setRole] = useState("");
  const [jdText, setJdText] = useState("");
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!role.trim() || !jdText.trim()) return;

    setLoading(true);
    setSuccess(false);
    setError(null);

    try {
      await api.post("/jd", { role, jdText });
      setSuccess(true);
      setRole("");
      setJdText("");
      if (onAdded) onAdded();
    } catch (err) {
      setError("Failed to save job description. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="bg-white border border-slate-200 rounded-2xl shadow-sm overflow-hidden w-full max-w-2xl mx-auto">
      {/* Header */}
      <div className="bg-gradient-to-r from-slate-800 to-slate-700 px-6 py-4">
        <h2 className="text-white font-semibold text-sm">
          New Job Description
        </h2>
        <p className="text-slate-400 text-xs mt-0.5">
          Parsed and added to the matching pool
        </p>
      </div>

      <form onSubmit={handleSubmit} className="p-6 space-y-5">
        {/* Role input */}
        <div>
          <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wide mb-2">
            Role Title
          </label>
          <input
            type="text"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            placeholder="e.g. Senior Backend Developer"
            className="w-full border border-slate-200 rounded-xl px-4 py-2.5 text-sm text-slate-800
              placeholder-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent
              transition-all duration-200"
          />
        </div>

        {/* JD  */}
        <div>
          <label className="block text-xs font-semibold text-slate-500 uppercase tracking-wide mb-2">
            Job Description Text
          </label>
          <textarea
            value={jdText}
            onChange={(e) => setJdText(e.target.value)}
            rows={8}
            placeholder="Paste the full job description here..."
            className="w-full border border-slate-200 rounded-xl px-4 py-3 text-sm text-slate-800
              placeholder-slate-300 focus:outline-none focus:ring-2 focus:ring-blue-400 focus:border-transparent
              transition-all duration-200 resize-none leading-relaxed"
          />
          <p className="text-xs text-slate-400 mt-1.5 text-right">
            {jdText.length} characters
          </p>
        </div>

        {/* Submit */}
        <button
          type="submit"
          disabled={loading || !role.trim() || !jdText.trim()}
          className="w-full bg-gradient-to-r from-blue-600 to-indigo-600 hover:from-blue-700 hover:to-indigo-700
            disabled:from-slate-300 disabled:to-slate-300 text-white font-semibold py-3 rounded-xl
            transition-all duration-200 shadow-sm hover:shadow-md text-sm flex items-center justify-center gap-2"
        >
          {loading ? (
            <>
              <svg
                className="w-4 h-4 animate-spin"
                fill="none"
                viewBox="0 0 24 24"
              >
                <circle
                  className="opacity-25"
                  cx="12"
                  cy="12"
                  r="10"
                  stroke="currentColor"
                  strokeWidth="4"
                />
                <path
                  className="opacity-75"
                  fill="currentColor"
                  d="M4 12a8 8 0 018-8v8z"
                />
              </svg>
              Parsing and Saving...
            </>
          ) : (
            "Add Job Description"
          )}
        </button>

        {/* Success */}
        {success && (
          <div className="flex items-center gap-2 bg-green-50 border border-green-200 rounded-xl px-4 py-3">
            <svg
              className="w-4 h-4 text-green-500 shrink-0"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.857-9.809a.75.75 0 00-1.214-.882l-3.483 4.79-1.88-1.88a.75.75 0 10-1.06 1.061l2.5 2.5a.75.75 0 001.137-.089l4-5.5z"
                clipRule="evenodd"
              />
            </svg>
            <p className="text-green-700 text-sm font-medium">
              Job description added successfully.
            </p>
          </div>
        )}

        {/* Error */}
        {error && (
          <div className="flex items-center gap-2 bg-red-50 border border-red-200 rounded-xl px-4 py-3">
            <svg
              className="w-4 h-4 text-red-500 shrink-0"
              fill="currentColor"
              viewBox="0 0 20 20"
            >
              <path
                fillRule="evenodd"
                d="M10 18a8 8 0 100-16 8 8 0 000 16zm-.75-4.75a.75.75 0 001.5 0v-4.5a.75.75 0 00-1.5 0v4.5zm.75-7.5a.75.75 0 100 1.5.75.75 0 000-1.5z"
                clipRule="evenodd"
              />
            </svg>
            <p className="text-red-600 text-sm">{error}</p>
          </div>
        )}
      </form>
    </div>
  );
}

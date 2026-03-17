export default function ParsedResumeCard({ resume }) {
  const fields = [
    { label: "Name", value: resume.name },
    {
      label: "Experience",
      value:
        resume.yearOfExperience != null
          ? `${resume.yearOfExperience} years`
          : null,
    },
    { label: "Salary", value: resume.salary },
    { label: "File", value: resume.fileName },
  ];

  return (
    <div className="bg-white border border-slate-200 rounded-2xl shadow-sm overflow-hidden w-full max-w-xl mx-auto">
      {/* Card header */}
      <div className="bg-gradient-to-r from-blue-600 to-indigo-600 px-6 py-4">
        <h2 className="text-white font-semibold text-sm">
          Resume Parsed Successfully
        </h2>
        <p className="text-blue-100 text-xs mt-0.5">
          {resume.resumeSkills?.length || 0} skills detected
        </p>
      </div>

      <div className="p-6">
        {/* Info grid */}
        <div className="grid grid-cols-2 gap-3 mb-5">
          {fields.map(({ label, value }) => (
            <div key={label} className="bg-slate-50 rounded-xl px-4 py-3">
              <p className="text-xs text-slate-400 font-medium mb-0.5">
                {label}
              </p>
              <p className="text-sm font-semibold text-slate-700 truncate">
                {value || "—"}
              </p>
            </div>
          ))}
        </div>

        {/* Skills */}
        <div>
          <p className="text-xs font-semibold text-slate-500 uppercase tracking-wide mb-3">
            Detected Skills
          </p>
          <div className="flex flex-wrap gap-2">
            {resume.resumeSkills?.map((skill) => (
              <span
                key={skill}
                className="bg-blue-50 text-blue-700 border border-blue-100 text-xs font-medium px-3 py-1 rounded-full"
              >
                {skill}
              </span>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

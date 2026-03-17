export default function ParsedResumeCard({ resume }) {
  return (
    <div className="bg-white border border-gray-200 rounded-xl p-6 shadow-sm w-full max-w-xl mx-auto mt-6">
      <h2 className="text-xl font-semibold text-gray-800 mb-4">
        Parsed Resume
      </h2>

      <div className="space-y-2 text-sm text-gray-700">
        <div className="flex justify-between">
          <span className="font-medium text-gray-500">Name</span>
          <span>{resume.name || "—"}</span>
        </div>
        <div className="flex justify-between">
          <span className="font-medium text-gray-500">Experience</span>
          <span>
            {resume.yearOfExperience != null
              ? `${resume.yearOfExperience} years`
              : "—"}
          </span>
        </div>
        <div className="flex justify-between">
          <span className="font-medium text-gray-500">Expected Salary</span>
          <span>{resume.salary || "—"}</span>
        </div>
        <div className="flex justify-between">
          <span className="font-medium text-gray-500">File</span>
          <span className="text-blue-500">{resume.fileName}</span>
        </div>
      </div>

      {/* Skills */}
      <div className="mt-4">
        <p className="text-sm font-medium text-gray-500 mb-2">
          Skills Found ({resume.resumeSkills?.length || 0})
        </p>
        <div className="flex flex-wrap gap-2">
          {resume.resumeSkills?.map((skill) => (
            <span
              key={skill}
              className="bg-blue-100 text-blue-700 text-xs font-medium px-2.5 py-1 rounded-full"
            >
              {skill}
            </span>
          ))}
        </div>
      </div>
    </div>
  );
}

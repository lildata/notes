Object Git {
	val commands = LinkedHashMap(
		"git init" -> "Initialize a repository and create .git/",
		"git status" -> "status of the sync, if there is something to comit...",
		"git add git.note.scala" -> "Add the file to the Staging Area"
		"git commit -m \"a message\"" -> "store the staged changes with a message",
		"git log" -> "see the changes/commits journal",
		"git remote add origin https://github.com/lildata/notes.git" -> "?",
		"git push -u origin master" -> "push the local changes to the origin repo, thanks to -u you can then just do git push",
		"git pull origin master" -> "pull the changes from the origin repo",
		"git diff HEAD" -> "get the diff of the most recent commit, refered as the HEAD pointer",
		"git diff --staged" -> "diff but for the files that have been staged",
		"git reset somefile.txt" -> "unstage a file",
		"git checkout -- git.note.scala" -> "get rid of all the changes since the last commit for git.note.scala",
		"git branch mysamplebranch" -> "create a branch",
		"git checkout mysamplebranch" -> "switch to mysamplbranch branch",
		"git rm somefile.txt" -> "remove the file on disk + stage the removal of the file on the repo",
		"git merge mysamplebranch" -> "merge a branch to the master branch",
		"git branch -d mysamplebranch" -> "delete the branch"		
	)
}

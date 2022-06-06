for file in test-files/*.md;
do
  java MarkdownParse $file

  echo $fileName
done
